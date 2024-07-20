package com.example.api.exercise.adapter.`in`.response

import com.example.core.exercise.dto.QueryItemDto

data class ExerciseItemDetailResponse(
    val id: Long,
    val exerciseName: String,
    val videoUri: List<String>,
    val imageUri: List<String>,
    val exerciseAreas: List<ExerciseAreaResponse>,
    val exerciseGoals: List<ExerciseGoalResponse>,
    val count: Int,
    val youtubeInfo: List<ItemYoutubeInfoResponse>,
) {
    companion object {
        @JvmStatic
        fun from(queryDto: QueryItemDto): ExerciseItemDetailResponse {
            val item = queryDto.item
            return ExerciseItemDetailResponse(
                id = item.id,
                imageUri = item.imageUrls,
                videoUri = item.videoUrls,
                exerciseName = item.exerciseName,
                exerciseAreas = queryDto.areas.map { area ->
                    ExerciseAreaResponse.from(area)
                },
                exerciseGoals = queryDto.goals.map { goal ->
                    ExerciseGoalResponse.from(goal)
                },
                count = queryDto.count,
                youtubeInfo = queryDto.itemYoutubeInfo.map {
                    ItemYoutubeInfoResponse.fromDomain(it)
                },
            )
        }
    }
}
