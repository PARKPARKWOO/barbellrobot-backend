package com.example.`in`.common.filter

import com.example.application.common.log.logger
import com.example.`in`.common.annotation.PublicEndPoint
import com.example.`in`.common.annotation.RateLimit
import com.example.`in`.common.config.RateLimiter
import com.example.`in`.common.interceptor.getBearerTokenFromHeader
import io.github.bucket4j.BandwidthBuilder
import io.github.bucket4j.Bucket
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus.TOO_MANY_REQUESTS
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerMapping
import org.springframework.web.servlet.support.RequestContextUtils
import java.time.Duration
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

@Component
class RateLimitFilter : OncePerRequestFilter() {
    companion object {
        // interval
        val userQuotaBucket: ConcurrentHashMap<String, Bucket> = ConcurrentHashMap()

        /**
         * 1회 요청당 약 2000~3000 Token 사용 (운동 입력 안했을때)
         * gemini flash 기준 분당 15 요청, 100만 토큰 허용
         * draft 하게 계산 했을때 token quota 를 초과하진 않음
         * 추후 Batch 로 토큰 계산 및 quota 설정 필요
         */
        // greedy
        val aiQuotaBucket: ConcurrentHashMap<String, Bucket> = ConcurrentHashMap()

        const val AI_API = "ai"

        const val TOO_MANY_REQUEST = "Too many requests"

        const val AI_QUOTA = 15L

        const val AI_REFILL_TOKEN = 1L

        const val AI_REFILL_INTERVAL = 1L

        val AI_REFILL_UNIT = TimeUnit.MINUTES
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val handlerMethod = getHandlerMethod(request)
        handlerMethod?.let { handler ->
            val annotation = handler.getMethodAnnotation(RateLimit::class.java)
                ?: return filterChain.doFilter(request, response)
            val path = request.servletPath
            val key =
                if (handler.hasMethodAnnotation(PublicEndPoint::class.java)) {
                    path
                } else {
                    request.getBearerTokenFromHeader()
                }

            val rateLimiter = getRateLimiter(annotation, key)
            val userBucket = userQuotaBucket.computeIfAbsent(key) { getIntervalBucket(rateLimiter) }
            if (path.contains(AI_API)) {
                val aiBucket = aiQuotaBucket.computeIfAbsent(path) { getGreedyBucket() }
                val aiProbe = aiBucket.tryConsumeAndReturnRemaining(1)
                if (!aiProbe.isConsumed) {
                    response.status = TOO_MANY_REQUESTS.value()
                    response.writer.write(TOO_MANY_REQUEST)
                    return
                }
            }

            val userProbe = userBucket.tryConsumeAndReturnRemaining(1)
            if (userProbe.isConsumed) {
                filterChain.doFilter(request, response)
            } else {
                response.status = TOO_MANY_REQUESTS.value()
                response.writer.write(TOO_MANY_REQUEST)
            }
        } ?: return filterChain.doFilter(request, response)
    }

    private fun getHandlerMethod(request: HttpServletRequest): HandlerMethod? {
        val handlerMappingBeans: Map<String, HandlerMapping> =
            RequestContextUtils.findWebApplicationContext(request)?.getBeansOfType(
                HandlerMapping::class.java,
            ) ?: return null

        for (handlerMapping in handlerMappingBeans.values) {
            runCatching {
                val handler = handlerMapping.getHandler(request)?.handler
                if (handler is HandlerMethod) {
                    return handler
                }
            }.getOrNull()
        }
        return null
    }

    private fun getRateLimiter(
        rateLimit: RateLimit,
        key: String,
    ): RateLimiter = RateLimiter.custom()
        .key(key)
        .timeUnit(rateLimit.timeUnit)
        .quota(rateLimit.quota)
        .refillInterval(rateLimit.refillInterval)
        .refillTokens(rateLimit.refillTokens)
        .build()

    private fun getIntervalBucket(rateLimiter: RateLimiter): Bucket = Bucket.builder()
        .addLimit(
            BandwidthBuilder.builder()
                .capacity(rateLimiter.quota)
                .refillIntervally(
                    rateLimiter.refillTokens,
                    Duration.of(rateLimiter.refillInterval, rateLimiter.timeUnit.toChronoUnit()),
                )
                .build(),
        ).build()

    private fun getGreedyBucket(): Bucket = Bucket.builder()
        .addLimit(
            BandwidthBuilder.builder()
                .capacity(AI_QUOTA)
                .refillGreedy(
                    AI_REFILL_TOKEN,
                    Duration.of(AI_REFILL_INTERVAL, AI_REFILL_UNIT.toChronoUnit()),
                )
                .build(),
        ).build()
}
