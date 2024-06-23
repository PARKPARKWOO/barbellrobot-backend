package com.example.api.common.annotation

import java.util.concurrent.TimeUnit
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.FUNCTION

/**
 * quota 를 설정하고 동시에 받을 수 있는 요청 수는 refillTokens 와 refillInterval 로 제어한다.
 * ex) quota = 3
 * timeUnit Seconds
 * refillTokens = 1
 * refillInterval = 1
 * 이라면 한번에 3개의 요청을 받을 수 있고 refill 방식에 따라 1초마다 1개씩 추가 요청을 받을 수 있다.
 */

@Target(FUNCTION)
@Retention(RUNTIME)
annotation class RateLimit(
    val quota: Long,
    val timeUnit: TimeUnit,
    val refillTokens: Long,
    val refillInterval: Long,
)
