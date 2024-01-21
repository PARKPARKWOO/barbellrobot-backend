package com.example.core.common.config

import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisPassword
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class RedisConfig(
    @Value("\${spring.data.redis.host}")
    private val host: String,
    @Value("\${spring.data.redis.port}")
    private val port: Int,
    @Value("\${spring.data.redis.password}")
    private val password: String,
) {
    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        val config = RedisStandaloneConfiguration(host, port)
        config.setPassword(RedisPassword.of(password))
        return LettuceConnectionFactory(config)
    }

    @Bean
    fun redisTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate<String, String> {
        val redisTemplate = RedisTemplate<String, String>()
        redisTemplate.connectionFactory = redisConnectionFactory
        redisTemplate.setEnableTransactionSupport(true)

        val stringSerializer = StringRedisSerializer()

        redisTemplate.keySerializer = stringSerializer
        redisTemplate.valueSerializer = stringSerializer
        return redisTemplate
    }

    @Bean
    fun redissonClient(): RedissonClient {
        val config = org.redisson.config.Config()
        config.useSingleServer().setAddress("$REDISSON_PREFIX$host:$port")
        config.useSingleServer().setPassword(password)
        return Redisson.create(config)
    }

    @Bean
    fun transactionManager(): PlatformTransactionManager {
        return JpaTransactionManager()
    }
}

const val REDISSON_PREFIX = "redis://"
