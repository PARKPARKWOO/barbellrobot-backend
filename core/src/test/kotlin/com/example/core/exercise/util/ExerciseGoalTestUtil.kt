package com.example.core.exercise.util

import com.example.core.exercise.adapter.out.persistence.entity.ExerciseGoalEntity
import com.example.core.exercise.application.`in`.command.SaveExerciseGoalCommand

object ExerciseGoalTestUtil {
    const val id = 1L

    const val GOAL = "goal1"

    val saveExerciseGoalCommand = SaveExerciseGoalCommand(
        goal = "다이어트",
    )

    val entity = ExerciseGoalEntity(
        id = id,
        goal = GOAL,
    )

    val entityList = listOf<ExerciseGoalEntity>(entity)
}
