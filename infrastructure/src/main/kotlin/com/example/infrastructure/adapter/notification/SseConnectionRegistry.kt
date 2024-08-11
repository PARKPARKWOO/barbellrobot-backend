package com.example.infrastructure.adapter.notification

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

object SseConnectionRegistry {
    val SSE_CONNECTED_USER: ConcurrentHashMap<UUID, SseEmitter> = ConcurrentHashMap()
}
