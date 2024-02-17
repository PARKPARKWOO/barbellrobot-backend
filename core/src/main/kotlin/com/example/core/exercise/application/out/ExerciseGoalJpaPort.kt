package com.example.core.exercise.application.out

import com.example.core.exercise.adapter.out.persistence.entity.ExerciseGoalEntity
import com.example.core.exercise.application.`in`.command.SaveExerciseGoalCommand

interface ExerciseGoalJpaPort {
    fun saveExerciseGoal(command: SaveExerciseGoalCommand)

    fun getExerciseGoal(id: Long): ExerciseGoalEntity

    fun getExerciseGoals(ids: List<Long>): List<ExerciseGoalEntity>?

    fun delete(entity: ExerciseGoalEntity)
}
