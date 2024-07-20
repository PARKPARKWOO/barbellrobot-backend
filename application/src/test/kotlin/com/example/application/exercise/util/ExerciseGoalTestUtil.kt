package com.example.application.exercise.util

import com.example.core.exercise.model.ExerciseGoal
import com.example.core.exercise.port.command.SaveExerciseGoalCommand

object ExerciseGoalTestUtil {
    const val ID = 1L

    const val GOAL = "goal"

    val saveExerciseGoalCommand = SaveExerciseGoalCommand(
        goal = "다이어트",
    )

    val goalModel = ExerciseGoal(
        id = ID,
        goal = GOAL,
    )

    val entityList = listOf<ExerciseGoal>(goalModel)
}
