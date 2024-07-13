package com.example.core.exercise.port.out

import com.example.core.exercise.dto.QueryItemDto
import com.example.core.exercise.port.command.SaveExerciseItemOutCommand
import com.example.core.exercise.model.ExerciseItem

interface ExerciseItemJpaPort {
    fun saveExerciseItem(command: SaveExerciseItemOutCommand): Long

    fun queryItem(id: Long): QueryItemDto?

    fun findById(id: Long): ExerciseItem

    fun delete(id: Long)

    fun findAll(): List<ExerciseItem>

    fun findAllItemsQuery(): List<QueryItemDto>

    fun findInIds(ids: List<Long>): List<QueryItemDto>
}
