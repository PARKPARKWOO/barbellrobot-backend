package com.example.core.exercise.port.`in`

import com.example.core.exercise.port.command.SaveExerciseAreaCommand
import com.example.core.exercise.model.ExerciseArea

interface ExerciseAreaUseCase {
    fun saveExerciseArea(command: SaveExerciseAreaCommand)

    fun deleteExerciseArea(id: Long)

    fun getExerciseArea(id: Long): ExerciseArea

    fun getAllExerciseArea(): List<ExerciseArea>
}
