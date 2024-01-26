package com.example.core.common.redis

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class RedisDriver(
    private val redisTemplate: RedisTemplate<String, String>,
) {
    fun setValue(key: String, value: String, ttl: Long) {
        redisTemplate.opsForValue().set(key, value, ttl, TimeUnit.SECONDS)
    }

    fun getValue(key: String): String? {
        return redisTemplate.opsForValue().get(key)
    }
}
