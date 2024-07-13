package com.example.core.exercise.util

import com.example.core.exercise.adapter.out.persistence.entity.ExerciseAreaEntity
import com.example.core.exercise.port.command.SaveExerciseAreaCommand

object ExerciseAreaTestUtil {
    const val ID = 1L

    const val AREA = "area"

    val saveExerciseAreaCommand = SaveExerciseAreaCommand(
        area = "가슴",
    )

    val entity = ExerciseAreaEntity(
        id = ID,
        area = AREA,
    )

    val entityList = listOf(entity)
}
