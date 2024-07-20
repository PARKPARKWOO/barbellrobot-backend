package com.example.infrastructure.redis

import com.example.core.nosql.KeyValueStore
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class RedisDriver(
    private val redisTemplate: RedisTemplate<String, Any>,
) : KeyValueStore {
    override fun <T> setValue(key: String, value: T, ttl: Long) {
        redisTemplate.opsForValue().set(key, value!!, ttl, TimeUnit.SECONDS)
    }

    override fun <T> getValue(key: String, clazz: Class<T>): T? {
        val value = redisTemplate.opsForValue().get(key)
        return clazz.cast(value)
    }
}
