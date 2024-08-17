package com.example.`in`.api.test

import com.example.`in`.common.annotation.PublicEndPoint
import com.example.`in`.common.annotation.RateLimit
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.TimeUnit

@RestController
class TestController {
    @GetMapping("/api/v1/ratelimit/test/{response}")
    @RateLimit(quota = 3, timeUnit = TimeUnit.SECONDS, refillTokens = 1, refillInterval = 3)
    @PublicEndPoint
    fun rateLimitTest(@PathVariable response: String): String {
        return response
    }
}
