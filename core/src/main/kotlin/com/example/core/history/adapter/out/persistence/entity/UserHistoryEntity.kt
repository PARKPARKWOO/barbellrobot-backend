package com.example.core.history.adapter.out.persistence.entity

import com.example.domain.history.UserHistory
import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.annotations.UuidGenerator
import org.hibernate.type.SqlTypes
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

const val USER_HISTORY_TABLE_NAME = "user_history"

@Entity
@Table(name = USER_HISTORY_TABLE_NAME)
@DynamicUpdate
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
    @Column(name = "today_images")
    @ElementCollection
    @CollectionTable(name = "user_history_today_images", joinColumns = [JoinColumn(name = "user_history_id")])
    var todayImageIds: MutableList<String> = mutableListOf(),
    @Column(name = "today_video")
    @ElementCollection
    @CollectionTable(name = "user_history_today_video", joinColumns = [JoinColumn(name = "user_history_id")])
    var todayVideo: MutableList<String> = mutableListOf(),
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
            todayVideo = todayVideo,
            updatedAt = updatedAt,
            todayImages = todayImageIds,
        )
    }
}
