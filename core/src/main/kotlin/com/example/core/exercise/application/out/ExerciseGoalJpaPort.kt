package com.example.core.exercise.application.out

import com.example.core.exercise.application.`in`.command.SaveExerciseGoalCommand

interface ExerciseGoalJpaPort {
    fun saveExerciseGoal(command: SaveExerciseGoalCommand)
}
