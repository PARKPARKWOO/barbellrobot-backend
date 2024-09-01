package com.example.core.exercise.port.`in`

import com.example.core.exercise.dto.QueryItemDto
import com.example.core.exercise.port.command.AddItemYoutubeCommand
import com.example.core.exercise.port.command.SaveExerciseItemCommand
import com.example.core.exercise.model.ExerciseItem

interface ExerciseItemUseCase {
    suspend fun saveExerciseItem(command: SaveExerciseItemCommand)

    fun findById(id: Long): ExerciseItem

    fun queryItem(id: Long): QueryItemDto

    fun deleteItem(id: Long)

    fun findAll(): List<ExerciseItem>

    fun findAllItemsQuery(ids: List<Long>?): List<QueryItemDto>

    fun addYoutubeLink(command: AddItemYoutubeCommand)

    fun deleteYoutubeLink(id: Long)
}
