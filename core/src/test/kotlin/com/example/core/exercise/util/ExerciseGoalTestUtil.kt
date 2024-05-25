package com.example.core.exercise.util

import com.example.core.exercise.adapter.out.persistence.entity.ExerciseGoalEntity
import com.example.core.exercise.application.port.command.SaveExerciseGoalCommand

object ExerciseGoalTestUtil {
    const val ID = 1L

    const val GOAL = "goal"

    val saveExerciseGoalCommand = SaveExerciseGoalCommand(
        goal = "다이어트",
    )

    val entity = ExerciseGoalEntity(
        id = ID,
        goal = GOAL,
    )

    val entityList = listOf<ExerciseGoalEntity>(entity)
}
