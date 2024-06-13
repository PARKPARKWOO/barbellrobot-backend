package com.example.api.exercise.adapter.`in`.response

import com.example.domain.exercise.ExerciseItem

data class ExerciseItemResponse(
    val id: Long,
    val exerciseName: String,
    val videoUri: List<String>,
    val imageUri: List<String>,
) {
    companion object {
        fun from(domain: ExerciseItem): ExerciseItemResponse = ExerciseItemResponse(
            id = domain.id,
            exerciseName = domain.exerciseName,
            videoUri = domain.videoUrls,
            imageUri = domain.imageUrls,
        )
    }
}
