package com.example.core.exercise.application.port.command

import com.example.core.exercise.adapter.out.persistence.entity.ExerciseAreaEntity
import com.example.core.exercise.adapter.out.persistence.entity.ExerciseGoalEntity

data class SaveExerciseItemCommand(
    val exerciseName: String,
    val videoUri: String?,
    val imageUri: String?,
    var exerciseAreas: MutableList<Long>,
    var exerciseGoals: MutableList<Long>,
) {
    fun toOutCommand(
        exerciseAreas: MutableList<ExerciseAreaEntity>,
        exerciseGoals: MutableList<ExerciseGoalEntity>,
    ): SaveExerciseItemOutCommand {
        return SaveExerciseItemOutCommand(
            exerciseName = exerciseName,
            videoUri = videoUri,
            imageUri = imageUri,
            exerciseAreas = exerciseAreas,
            exerciseGoals = exerciseGoals,
        )
    }
}
