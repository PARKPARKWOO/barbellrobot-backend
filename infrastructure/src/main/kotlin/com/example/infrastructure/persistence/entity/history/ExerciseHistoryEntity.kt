package com.example.infrastructure.persistence.entity.history

import com.example.infrastructure.persistence.entity.exercise.EXERCISE_ITEM_ID
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.time.LocalDate
import java.util.UUID

const val EXERCISE_HISTORY_TABLE_NAME = "exercise_history"

@Entity
@Table(name = EXERCISE_HISTORY_TABLE_NAME)
class ExerciseHistoryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,
    @Column(name = EXERCISE_ITEM_ID)
    var itemId: Long,
    @Column(name = "weight")
    var weight: Double,
    @Column(name = "exercise_set")
    var exerciseSet: Int,
    @Column(name = "count", nullable = true)
    var count: Int?,
    @Column(name = "user_history_id")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    var userHistoryId: UUID,
    @Column(name = "created_at")
    var createdAt: LocalDate,
)
