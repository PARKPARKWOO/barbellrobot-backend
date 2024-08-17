package com.example.`in`.common.config

import java.util.concurrent.TimeUnit

class RateLimiter(
    val timeUnit: TimeUnit,
    val quota: Long,
    val key: String,
    val refillTokens: Long,
    val refillInterval: Long,
) {
    class Builder {
        private var timeUnit: TimeUnit = TimeUnit.SECONDS
        private var quota: Long = 0
        private var key: String = ""
        private var refillTokens: Long = 0
        private var refillInterval: Long = 0

        fun timeUnit(timeUnit: TimeUnit) = apply { this.timeUnit = timeUnit }
        fun key(key: String) = apply { this.key = key }
        fun quota(quota: Long) = apply { this.quota = quota }
        fun refillTokens(refillTokens: Long) = apply { this.refillTokens = refillTokens }
        fun refillInterval(refillInterval: Long) = apply { this.refillInterval = refillInterval }

        fun build(): RateLimiter {
            require(quota > 0) { "quota must be greater than 0" }
            require(refillTokens > 0) { "refillTokens must be greater than 0" }
            require(refillInterval > 0) { "refillInterval must be greater than 0" }
            require(key.isNotEmpty()) { "key must not be empty" }
            return RateLimiter(timeUnit, quota, key, refillTokens, refillInterval)
        }
    }

    companion object {
        fun custom() = Builder()
    }
}
