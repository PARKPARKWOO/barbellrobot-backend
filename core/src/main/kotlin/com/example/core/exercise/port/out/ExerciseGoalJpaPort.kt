package com.example.core.exercise.port.out

import com.example.core.exercise.model.ExerciseGoal
import com.example.core.exercise.port.command.SaveExerciseGoalCommand

interface ExerciseGoalJpaPort {
    fun saveExerciseGoal(command: SaveExerciseGoalCommand)

    fun getExerciseGoal(id: Long): ExerciseGoal?

    fun getExerciseGoals(ids: List<Long>): List<ExerciseGoal>

    fun delete(id: Long)

    fun getAll(): List<ExerciseGoal>?
}
