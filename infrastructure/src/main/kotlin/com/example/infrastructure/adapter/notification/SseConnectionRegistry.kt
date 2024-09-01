package com.example.infrastructure.adapter.notification

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

object SseConnectionRegistry {
    val SSE_CONNECTED_USER: ConcurrentMap<UUID, SseEmitter> = ConcurrentHashMap()
}
