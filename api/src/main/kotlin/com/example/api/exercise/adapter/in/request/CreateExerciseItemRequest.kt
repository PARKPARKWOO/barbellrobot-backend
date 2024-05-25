package com.example.api.exercise.adapter.`in`.request

import com.example.core.exercise.application.port.command.SaveExerciseItemCommand

data class CreateExerciseItemRequest(
    val exerciseName: String,
    val videoUri: String?,
    val imageUri: String?,
    var exerciseAreas: MutableList<Long>,
    var exerciseGoals: MutableList<Long>,
) {
    fun toCommand(): SaveExerciseItemCommand {
        return SaveExerciseItemCommand(
            exerciseName = exerciseName,
            videoUri = videoUri,
            imageUri = imageUri,
            exerciseAreas = exerciseAreas,
            exerciseGoals = exerciseGoals,
        )
    }
}
