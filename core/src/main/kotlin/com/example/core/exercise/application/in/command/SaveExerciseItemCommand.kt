package com.example.core.exercise.application.`in`.command

data class SaveExerciseItemCommand(
    val exerciseName: String,
    val videoUri: String?,
    val imageUri: String?,
    var exerciseAreas: MutableList<Long>,
    var exerciseGoals: MutableList<Long>,
)
