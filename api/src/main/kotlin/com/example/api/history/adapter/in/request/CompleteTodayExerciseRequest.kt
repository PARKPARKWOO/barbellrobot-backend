package com.example.api.history.adapter.`in`.request

import com.example.core.history.application.port.`in`.command.CompleteExerciseItem
import com.example.core.history.application.port.`in`.command.ExerciseTodayCommand
import java.util.UUID

data class CompleteTodayExerciseRequest(
    val userHistoryId: UUID,
    val items: List<CompleteExerciseItemRequest>,
) {
    fun toCommand(): ExerciseTodayCommand {
        return ExerciseTodayCommand(
            userHistoryId = userHistoryId,
            items = items.map { it.toCommand() },
        )
    }
}

data class CompleteExerciseItemRequest(
    val itemId: Long,
    val weight: Double?,
    val exerciseSet: Int,
) {
    fun toCommand(): CompleteExerciseItem {
        return CompleteExerciseItem(
            itemId = itemId,
            weight = weight ?: 0.0,
            exerciseSet = exerciseSet,
        )
    }
}
