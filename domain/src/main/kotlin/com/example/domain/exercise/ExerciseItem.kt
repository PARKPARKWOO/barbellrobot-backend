package com.example.domain.exercise

data class ExerciseItem(
    val id: Long,
    val exerciseName: String,
    val videoUri: String?,
    val imageUri: String?,
    val exerciseAreas: List<Long>,
    val exerciseGoals: List<Long>,
)
