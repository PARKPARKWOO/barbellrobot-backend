package com.example.application.exercise.util

import com.example.core.exercise.model.ExerciseArea
import com.example.core.exercise.port.command.SaveExerciseAreaCommand

object ExerciseAreaTestUtil {
    const val ID = 1L

    const val AREA = "area"

    val saveExerciseAreaCommand = SaveExerciseAreaCommand(
        area = "가슴",
    )

    val areaModel = ExerciseArea(
        id = ID,
        area = AREA,
    )

    val entityList = listOf(areaModel)
}
