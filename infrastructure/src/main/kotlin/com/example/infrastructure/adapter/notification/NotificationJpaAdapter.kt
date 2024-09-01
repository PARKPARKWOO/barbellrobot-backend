package com.example.infrastructure.adapter.notification

import com.example.core.event.NotificationEvent
import com.example.core.notification.model.Notification
import com.example.core.notification.port.out.NotificationJpaPort
import com.example.infrastructure.persistence.entity.notification.NotificationEntity
import com.example.infrastructure.persistence.repository.notification.NotificationRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class NotificationJpaAdapter(
    private val notificationRepository: NotificationRepository,
) : NotificationJpaPort {
    override fun saveEvent(notificationEvent: NotificationEvent) {
        val entity = NotificationEntity.createEntity(notificationEvent)
        notificationRepository.save(entity)
    }

    override fun findByRecentUnread(receiverId: UUID): List<Notification> {
        return notificationRepository.findRecentUnread(receiverId)
            .map { entity -> entity.toDomain() }
    }

    override fun update(notification: Notification) {
        val entity = NotificationEntity.from(notification)
        notificationRepository.save(entity)
    }
}
