package com.example.core.history.application.port.`in`.command

import java.util.UUID

data class ExerciseTodayCommand(
    val userHistoryId: UUID,
    // TODO 운동 세트, 무게, 종류
    val items: List<CompleteExerciseItem>,
)

data class CompleteExerciseItem(
    var itemId: Long,
    var weight: Double,
    var exerciseSet: Int,
)
