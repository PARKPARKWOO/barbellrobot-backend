package com.example.domain.exercise

data class ExerciseItem(
    val id: Long,
    val exerciseName: String,
    val videoUrls: List<String>,
    val imageUrls: List<String>,
)
