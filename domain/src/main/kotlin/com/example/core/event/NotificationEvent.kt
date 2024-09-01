package com.example.core.event

import com.example.core.notification.model.Notification
import java.util.UUID

data class NotificationEvent(
    val sender: UUID,
    val receiver: UUID,
    val type: NotificationEventType,
    val message: String = "",
) {
    companion object {
        fun from(domain: Notification): NotificationEvent = NotificationEvent(
            sender = domain.sender,
            receiver = domain.receiver,
            type = domain.type,
            message = domain.message,
        )
    }
}

enum class NotificationEventType {
    CHAT,
    RIVAL_ACCEPT,
    RIVAL_REQUEST,
    RIVAL_PROD,
}
