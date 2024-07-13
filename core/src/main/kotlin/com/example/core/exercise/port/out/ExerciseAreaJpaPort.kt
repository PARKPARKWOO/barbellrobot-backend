package com.example.core.exercise.port.out

import com.example.core.exercise.adapter.out.persistence.entity.ExerciseAreaEntity
import com.example.core.exercise.port.command.SaveExerciseAreaCommand
import com.example.core.exercise.model.ExerciseArea

interface ExerciseAreaJpaPort {
    fun saveExerciseArea(command: SaveExerciseAreaCommand)

    fun getExerciseArea(id: Long): ExerciseArea

    fun getExerciseAreas(ids: List<Long>): List<ExerciseAreaEntity>?

    fun delete(id: Long)

    fun getAll(): List<ExerciseArea>?
}
