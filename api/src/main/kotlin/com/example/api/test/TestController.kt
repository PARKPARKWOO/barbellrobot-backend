package com.example.api.test

import com.example.api.common.annotation.PublicEndPoint
import com.example.api.common.annotation.RateLimit
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.TimeUnit

@RestController
class TestController {
    @GetMapping("/api/v1/ratelimit/test")
    @RateLimit(quota = 3, timeUnit = TimeUnit.SECONDS, refillTokens = 1, refillInterval = 3)
    @PublicEndPoint
    fun rateLimitTest(): String {
        return "success"
    }
}
