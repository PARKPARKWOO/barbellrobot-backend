package com.example.core.exercise.model

data class ExerciseItem(
    val id: Long,
    val exerciseName: String,
    val videoUrls: List<String>,
    val imageUrls: List<String>,
)
