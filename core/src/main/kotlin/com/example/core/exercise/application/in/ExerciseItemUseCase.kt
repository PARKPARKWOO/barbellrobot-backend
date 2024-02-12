package com.example.core.exercise.application.`in`

import com.example.core.exercise.application.`in`.command.SaveExerciseItemCommand

interface ExerciseItemUseCase {
    fun saveExerciseItem(command: SaveExerciseItemCommand)
}
