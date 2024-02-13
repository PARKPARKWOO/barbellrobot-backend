package com.example.core.exercise.application.`in`

import com.example.core.exercise.application.`in`.command.SaveExerciseGoalCommand

interface ExerciseGoalUseCase {
    fun saveExerciseGoal(command: SaveExerciseGoalCommand)

    fun deleteExerciseGoal(id: Long)
}
