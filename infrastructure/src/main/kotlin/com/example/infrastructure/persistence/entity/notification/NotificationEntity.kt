package com.example.infrastructure.persistence.entity.notification

import com.example.core.event.NotificationEvent
import com.example.core.event.NotificationEventType
import com.example.core.notification.model.Notification
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.util.UUID

const val NOTIFICATION_TABLE_NAME = "notification"

@Entity
@Table(name = NOTIFICATION_TABLE_NAME)
class NotificationEntity(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long,
    @Column(name = "is_sent")
    var isSent: Boolean,
    @Column(name = "is_read")
    var isRead: Boolean,
    @Column(name = "sender")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    val sender: UUID,
    @Column(name = "receiver")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    val receiver: UUID,
    @Column(name = "message")
    val message: String,
    @Enumerated(STRING)
    val type: NotificationEventType,
) {
    companion object {
        fun createEntity(event: NotificationEvent): NotificationEntity = NotificationEntity(
            id = 0L,
            isSent = false,
            isRead = false,
            sender = event.sender,
            receiver = event.receiver,
            type = event.type,
            message = event.message,
        )
        fun from(domain: Notification): NotificationEntity = NotificationEntity(
            id = domain.id,
            isSent = domain.isSent,
            isRead = domain.isRead,
            sender = domain.sender,
            receiver = domain.receiver,
            type = domain.type,
            message = domain.message,
        )
    }

    fun toDomain(): Notification = Notification(
        id = id,
        isSent = isSent,
        isRead = isRead,
        sender = sender,
        receiver = receiver,
        type = type,
        message = message,
    )
}
