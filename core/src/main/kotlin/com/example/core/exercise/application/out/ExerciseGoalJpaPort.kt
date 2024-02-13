package com.example.core.exercise.application.out

import com.example.core.exercise.application.`in`.command.SaveExerciseGoalCommand
import com.example.domain.exercise.ExerciseGoal

interface ExerciseGoalJpaPort {
    fun saveExerciseGoal(command: SaveExerciseGoalCommand)

    fun getExerciseGoal(id: Long): ExerciseGoal

    fun getExerciseGoals(ids: List<Long>): List<ExerciseGoal>?

    fun delete(id: Long)
}
