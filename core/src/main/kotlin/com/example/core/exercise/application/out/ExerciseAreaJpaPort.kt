package com.example.core.exercise.application.out

import com.example.core.exercise.application.`in`.command.SaveExerciseAreaCommand
import com.example.domain.exercise.ExerciseArea

interface ExerciseAreaJpaPort {
    fun saveExerciseArea(command: SaveExerciseAreaCommand)

    fun getExerciseArea(id: Long): ExerciseArea

    fun getExerciseAreas(ids: List<Long>): List<ExerciseArea>?

    fun delete(id: Long)
}
