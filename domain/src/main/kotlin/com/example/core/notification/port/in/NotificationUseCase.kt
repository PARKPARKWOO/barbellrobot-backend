package com.example.core.notification.port.`in`

import com.example.core.event.NotificationEvent
import com.example.core.notification.model.Notification
import java.util.UUID

interface NotificationUseCase {
    fun sendNotification(notificationEvent: NotificationEvent)

    fun sendNotification(notification: Notification)

    fun findRecentUnreadNotification(receiverId: UUID): List<Notification>
}
