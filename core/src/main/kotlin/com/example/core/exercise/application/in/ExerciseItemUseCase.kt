package com.example.core.exercise.application.`in`

import com.example.core.exercise.application.dto.QueryItemDto
import com.example.core.exercise.application.`in`.command.SaveExerciseItemCommand
import com.example.domain.exercise.ExerciseItem

interface ExerciseItemUseCase {
    fun saveExerciseItem(command: SaveExerciseItemCommand)

    fun findById(id: Long): ExerciseItem

    fun queryItem(id: Long): QueryItemDto
}
