package com.example.core.exercise.application.out

import com.example.core.exercise.application.`in`.command.SaveExerciseAreaCommand

interface ExerciseAreaJpaPort {
    fun saveExerciseArea(command: SaveExerciseAreaCommand)
}
