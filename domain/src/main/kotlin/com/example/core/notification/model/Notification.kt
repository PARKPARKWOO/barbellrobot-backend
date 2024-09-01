package com.example.core.notification.model

import com.example.core.event.NotificationEventType
import java.util.UUID

data class Notification(
    val id: Long,
    var isSent: Boolean,
    var isRead: Boolean,
    val sender: UUID,
    val receiver: UUID,
    val message: String,
    val type: NotificationEventType,
) {
    fun read(): Notification = this.apply {
        isRead = true
    }

    fun sent(): Notification = this.apply {
        isSent = true
    }
}
