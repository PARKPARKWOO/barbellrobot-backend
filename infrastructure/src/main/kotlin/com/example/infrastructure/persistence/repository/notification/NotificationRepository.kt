package com.example.infrastructure.persistence.repository.notification

import com.example.infrastructure.persistence.entity.notification.NotificationEntity
import com.example.infrastructure.persistence.entity.notification.QNotificationEntity.notificationEntity
import com.querydsl.jpa.JPAExpressions
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

interface NotificationRepository : JpaRepository<NotificationEntity, Long>, NotificationQueryRepository

interface NotificationQueryRepository {
    fun findRecentUnread(receiverId: UUID): List<NotificationEntity>
}

@Repository
class NotificationQueryRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : NotificationQueryRepository {
    override fun findRecentUnread(receiverId: UUID): List<NotificationEntity> {
        val latestEventsSubQuery = JPAExpressions.select(notificationEntity.id.max())
            .from(notificationEntity)
            .where(
                notificationEntity.isRead.isFalse
                    .and(notificationEntity.receiver.eq(receiverId)),
            )
            .groupBy(notificationEntity.type)

        return jpaQueryFactory.selectFrom(notificationEntity)
            .where(notificationEntity.id.`in`(latestEventsSubQuery))
            .fetch()
    }
}
