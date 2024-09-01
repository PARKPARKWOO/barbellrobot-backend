package com.example.`in`.scheduler

import com.example.application.common.log.logger
import com.example.infrastructure.adapter.notification.SseConnectionRegistry
import com.example.infrastructure.adapter.notification.SseConnectionRegistry.SSE_CONNECTED_USER
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@Component
class SseHeartbeatScheduler {
    private val log = logger()

    companion object {
        private const val HEARTBEAT_INTERVAL = 3000L
        private const val HEARTBEAT_EVENT_NAME = "heartbeat"
    }

    @Scheduled(fixedDelay = HEARTBEAT_INTERVAL)
    fun sendHeartbeat() {
        log.info(Thread.currentThread().name)
        SSE_CONNECTED_USER.forEach { (userId, sseEmitter) ->
            runCatching {
                sseEmitter.send(SseEmitter.event().name(HEARTBEAT_EVENT_NAME))
            }.onFailure {
                SSE_CONNECTED_USER.remove(userId)
            }
        }
    }
}
