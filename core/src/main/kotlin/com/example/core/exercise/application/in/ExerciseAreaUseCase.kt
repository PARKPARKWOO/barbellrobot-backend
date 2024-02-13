package com.example.core.exercise.application.`in`

import com.example.core.exercise.application.`in`.command.SaveExerciseAreaCommand

interface ExerciseAreaUseCase {
    fun saveExerciseArea(command: SaveExerciseAreaCommand)

    fun deleteExerciseArea(id: Long)
}
