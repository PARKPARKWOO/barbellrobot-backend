package com.example.core.notification.port.out

import com.example.core.event.NotificationEvent
import com.example.core.notification.model.Notification
import java.util.UUID

interface NotificationJpaPort {
    fun saveEvent(notificationEvent: NotificationEvent)

    fun findByRecentUnread(receiverId: UUID): List<Notification>

    fun update(notification: Notification)
}
