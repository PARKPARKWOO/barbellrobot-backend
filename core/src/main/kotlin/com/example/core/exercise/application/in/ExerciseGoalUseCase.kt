package com.example.core.exercise.application.`in`

import com.example.core.exercise.application.`in`.command.SaveExerciseGoalCommand
import com.example.domain.exercise.ExerciseGoal

interface ExerciseGoalUseCase {
    fun saveExerciseGoal(command: SaveExerciseGoalCommand)

    fun deleteExerciseGoal(id: Long)

    fun getGoal(id: Long): ExerciseGoal

    fun getAllGoals(): List<ExerciseGoal>
}
