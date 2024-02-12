package com.example.core.exercise.application.out

import com.example.core.exercise.application.`in`.command.SaveExerciseItemCommand

interface ExerciseItemJpaPort {
    fun saveExerciseItem(command: SaveExerciseItemCommand)
}
