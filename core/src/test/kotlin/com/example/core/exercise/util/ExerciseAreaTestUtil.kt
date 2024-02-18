package com.example.core.exercise.util

import com.example.core.exercise.adapter.out.persistence.entity.ExerciseAreaEntity
import com.example.core.exercise.application.`in`.command.SaveExerciseAreaCommand

object ExerciseAreaTestUtil {
    const val id = 1L

    val saveExerciseAreaCommand = SaveExerciseAreaCommand(
        area = "가슴",
    )

    val entity = ExerciseAreaEntity(
        id = id,
        area = "area1",
    )

    val entityList = listOf(entity)
}
