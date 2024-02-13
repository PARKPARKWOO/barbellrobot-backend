package com.example.core.exercise.application.out

import com.example.core.exercise.application.`in`.command.SaveExerciseItemCommand

interface ExerciseItemJpaPort {
    fun saveExerciseItem(command: SaveExerciseItemCommand)

    fun findByExerciseAreaId(exerciseAreaId: Long)

    fun findByExerciseGoalId(exerciseGoalId: Long)

    fun removeExerciseArea(exerciseAreaId: Long)

    fun removeExerciseGoals(exerciseGoalId: Long)
}
