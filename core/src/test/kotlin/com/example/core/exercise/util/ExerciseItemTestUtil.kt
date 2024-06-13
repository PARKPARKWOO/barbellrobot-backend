package com.example.core.exercise.util

import com.example.core.exercise.application.port.command.SaveExerciseItemCommand
import org.springframework.mock.web.MockMultipartFile

object ExerciseItemTestUtil {
    const val ID = 1L
    const val IMAGE_URI = "http://~"
    const val VIDEO_URI = "http://~"
    const val EXERCISE_NAME = "벤치프레스"
    val goalIds = listOf(1L)
    val areaIds = listOf(1L)
    val saveCommand = SaveExerciseItemCommand(
        exerciseName = EXERCISE_NAME,
        video = null,
        image = null,
        exerciseGoals = goalIds.toMutableList(),
        exerciseAreas = areaIds.toMutableList(),
    )
}
