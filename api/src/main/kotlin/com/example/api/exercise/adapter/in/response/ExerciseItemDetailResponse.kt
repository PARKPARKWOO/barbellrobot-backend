package com.example.api.exercise.adapter.`in`.response

import com.example.core.exercise.application.dto.QueryItemDto

data class ExerciseItemDetailResponse(
    val id: Long,
    val exerciseName: String,
    val videoUri: String?,
    val imageUri: String?,
    val exerciseAreas: List<ExerciseAreaResponse>,
    val exerciseGoals: List<ExerciseGoalResponse>,
    val count: Int,
) {
    companion object {
        @JvmStatic
        fun from(queryDto: QueryItemDto): ExerciseItemDetailResponse {
            val item = queryDto.item
            return ExerciseItemDetailResponse(
                id = item.id,
                imageUri = item.imageUri,
                videoUri = item.videoUri,
                exerciseName = item.exerciseName,
                exerciseAreas = queryDto.areas.map { area ->
                    ExerciseAreaResponse.from(area)
                },
                exerciseGoals = queryDto.goals.map { goal ->
                    ExerciseGoalResponse.from(goal)
                },
                count = queryDto.count,
            )
        }
    }
}
