package com.example.application.notification

import com.example.application.common.transaction.Tx
import com.example.core.event.NotificationEvent
import com.example.core.notification.model.Notification
import com.example.core.notification.port.`in`.NotificationUseCase
import com.example.core.notification.port.out.NotificationJpaPort
import com.example.core.notification.port.out.NotificationPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class NotificationService(
    private val notificationPort: NotificationPort,
    private val notificationJpaPort: NotificationJpaPort,
) : NotificationUseCase {
    override fun sendNotification(notificationEvent: NotificationEvent) {
        val isSent = notificationPort.sendNotification(notificationEvent)
        if (!isSent) {
            Tx.writeTx { notificationJpaPort.saveEvent(notificationEvent) }
        }
    }

    override fun sendNotification(notification: Notification) {
        val isSend = notificationPort.sendNotification(NotificationEvent.from(notification))
        if (isSend) {
            notification.sent()
            notification.read()
            Tx.writeTx { notificationJpaPort.ackNotification(notification) }
        }
    }

    @Transactional(readOnly = true)
    override fun findRecentUnreadNotification(receiverId: UUID): List<Notification> {
        return notificationJpaPort.findByRecentUnread(receiverId)
    }
}
