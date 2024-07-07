package com.example.core.history.adapter.out.persistence.entity

import com.example.core.history.model.UserHistory
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.annotations.UuidGenerator
import org.hibernate.type.SqlTypes
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

const val USER_HISTORY_TABLE_NAME = "user_history"

@Entity
@Table(name = USER_HISTORY_TABLE_NAME)
@DynamicUpdate
@EntityListeners(AuditingEntityListener::class)
class UserHistoryEntity(
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    var id: UUID = UUID.randomUUID(),
    @Column(name = "today")
    @CreatedDate
    @NotNull
    var today: LocalDate,
    @Column(name = "user_id")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    var userId: UUID,
    @Column(name = "attendance")
    var attendance: Boolean = true,
) {
    @Column(name = "updated_at")
    @LastModifiedDate
    @NotNull
    lateinit var updatedAt: LocalDateTime

    fun toDomain(): UserHistory {
        return UserHistory(
            id = id,
            userId = userId,
            createdAt = today,
            updatedAt = updatedAt,
        )
    }
}
