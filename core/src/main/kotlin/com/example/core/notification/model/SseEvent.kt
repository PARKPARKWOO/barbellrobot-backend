package com.example.core.notification.model

import java.util.UUID

data class SseEvent(
    val sender: UUID,
    val receiver: UUID,
    val type: SseEventType,
    val message: String,
)

enum class SseEventType {
    CHAT,
    RIVAL_ACCEPT,
    RIVAL_REQUEST,
}
