package com.example.core.event

import java.util.UUID

data class SseConnectEvent(
    val userId: UUID,
    val type: SseConnectType,
)

enum class SseConnectType {
    CONNECTED,
    DISCONNECTED
}
