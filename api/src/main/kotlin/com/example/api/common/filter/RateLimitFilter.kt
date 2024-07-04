package com.example.api.common.filter

import com.example.api.common.annotation.PublicEndPoint
import com.example.api.common.annotation.RateLimit
import com.example.api.common.config.RateLimiter
import com.example.api.common.interceptor.getBearerTokenFromHeader
import com.example.common.log.Log
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

@Component
class RateLimitFilter : OncePerRequestFilter(), Log {
    companion object {
        // interval
        val userQuotaBucket: ConcurrentHashMap<String, Bucket> = ConcurrentHashMap()

        // greedy
        val aiQuotaBucket: ConcurrentHashMap<String, Bucket> = ConcurrentHashMap()

        const val AI_API = "ai"

        const val TOO_MANY_REQUEST = "Too many requests"
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
                val aiBucket = aiQuotaBucket.computeIfAbsent(path) { getGreedyBucket(rateLimiter) }
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

    private fun getGreedyBucket(rateLimiter: RateLimiter): Bucket = Bucket.builder()
        .addLimit(
            BandwidthBuilder.builder()
                .capacity(rateLimiter.quota)
                .refillGreedy(
                    rateLimiter.refillTokens,
                    Duration.of(rateLimiter.refillInterval, rateLimiter.timeUnit.toChronoUnit()),
                )
                .build(),
        ).build()
}
