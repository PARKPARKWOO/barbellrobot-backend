package com.example.domain.exercise

data class ExerciseItem(
    var id: Long,
    var exerciseName: String,
    var videoUri: String?,
    var imageUri: String?,
    var exerciseAreas: List<Long>
)
