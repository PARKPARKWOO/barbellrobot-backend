package com.example.core.exercise.util

import com.example.core.exercise.application.`in`.command.SaveExerciseItemCommand

object ExerciseItemTestUtil {
    const val id = 1L
    val goalIds = listOf(1L)
    val areaIds = listOf(1L)
    val saveCommand = SaveExerciseItemCommand(
        exerciseName = "벤치프레스",
        videoUri = "http://~",
        imageUri = "http://~",
        exerciseGoals = goalIds.toMutableList(),
        exerciseAreas = areaIds.toMutableList(),
    )
}
