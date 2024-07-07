package com.example.application.common.aop

import com.example.common.log.Log
import com.example.common.parser.SpringElParser
import com.example.core.common.annotation.DistributedLock
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component

@Component
@Aspect
@Suppress("RethrowCaughtException")
class DistributedLockAop(
    private val redissonClient: RedissonClient,
    private val aopForTransaction: AopForTransaction,
) : Log {
    companion object {
        const val REDISSON_LOCK_PREFIX = "LOCK:"
    }

    @Around("@annotation(com.example.core.common.annotation.DistributedLock)")
    fun lock(joinPoint: ProceedingJoinPoint): Any? {
        val signature = joinPoint.signature as MethodSignature
        val method = signature.method
        val annotation = method.getAnnotation(DistributedLock::class.java)

        val key: String = REDISSON_LOCK_PREFIX + SpringElParser.getDynamicValue(
            signature.parameterNames,
            joinPoint.args,
            annotation.key,
        ).toString()
        val lock = redissonClient.getLock(key)

        try {
            val tryLock = lock.tryLock(annotation.waitTime, annotation.leaseTime, annotation.timeUnit)
            if (!tryLock) {
                return false
            }
            return aopForTransaction.proceed(joinPoint)
        } finally {
            try {
                lock.unlock()
            } catch (e: IllegalMonitorStateException) {
                log.warn("Redisson lock already unlock method = ${method.name} key = $key")
            }
        }
    }
}
