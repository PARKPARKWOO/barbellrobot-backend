package com.example.core.exercise.util

import com.example.core.exercise.application.`in`.command.SaveExerciseItemCommand

object ExerciseItemTestUtil {
    const val ID = 1L
    const val IMAGE_URI = "http://~"
    const val VIDEO_URI = "http://~"
    const val EXERCISE_NAME = "벤치프레스"
    val goalIds = listOf(1L)
    val areaIds = listOf(1L)
    val saveCommand = SaveExerciseItemCommand(
        exerciseName = EXERCISE_NAME,
        videoUri = VIDEO_URI,
        imageUri = IMAGE_URI,
        exerciseGoals = goalIds.toMutableList(),
        exerciseAreas = areaIds.toMutableList(),
    )
}
