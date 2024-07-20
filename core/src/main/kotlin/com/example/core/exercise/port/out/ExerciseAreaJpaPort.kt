package com.example.core.exercise.port.out

import com.example.core.exercise.model.ExerciseArea
import com.example.core.exercise.port.command.SaveExerciseAreaCommand

interface ExerciseAreaJpaPort {
    fun saveExerciseArea(command: SaveExerciseAreaCommand)

    fun getExerciseArea(id: Long): ExerciseArea?

    fun getExerciseAreas(ids: List<Long>): List<ExerciseArea>

    fun delete(id: Long)

    fun getAll(): List<ExerciseArea>
}
