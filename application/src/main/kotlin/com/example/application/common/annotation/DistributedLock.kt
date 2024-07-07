package com.example.application.common.annotation

import java.util.concurrent.TimeUnit

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class DistributedLock(
    val key: String,
    val timeUnit: TimeUnit = TimeUnit.SECONDS,
    val leaseTime: Long = 3L,
    val waitTime: Long = 5L,
)
