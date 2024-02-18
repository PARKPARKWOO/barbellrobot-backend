package com.example.core.exercise.util

import com.example.core.exercise.adapter.out.persistence.entity.ExerciseGoalEntity
import com.example.core.exercise.application.`in`.command.SaveExerciseGoalCommand

object ExerciseGoalTestUtil {
    const val id = 1L
    val saveExerciseGoalCommand = SaveExerciseGoalCommand(
        goal = "다이어트",
    )

    val entity = ExerciseGoalEntity(
        id = id,
        goal = "goal1",
    )

    val entityList = listOf<ExerciseGoalEntity>(entity)
}
