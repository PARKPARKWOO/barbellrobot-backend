package com.example.core.exercise.application.port.out

import com.example.core.exercise.adapter.out.persistence.entity.ExerciseGoalEntity
import com.example.core.exercise.application.port.command.SaveExerciseGoalCommand
import com.example.core.exercise.model.ExerciseGoal

interface ExerciseGoalJpaPort {
    fun saveExerciseGoal(command: SaveExerciseGoalCommand)

    fun getExerciseGoal(id: Long): ExerciseGoalEntity

    fun getExerciseGoals(ids: List<Long>): List<ExerciseGoalEntity>?

    fun delete(entity: ExerciseGoalEntity)

    fun getAll(): List<ExerciseGoal>?
}
