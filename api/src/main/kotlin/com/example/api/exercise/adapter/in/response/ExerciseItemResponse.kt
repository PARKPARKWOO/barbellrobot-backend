package com.example.api.exercise.adapter.`in`.response

import com.example.domain.exercise.ExerciseArea
import com.example.domain.exercise.ExerciseGoal
import com.example.domain.exercise.ExerciseItem

data class ExerciseItemResponse(
    val id: Long,
    val exerciseName: String,
    val videoUri: String?,
    val imageUri: String?,
    val exerciseAreas: List<ExerciseAreaResponse>?,
    val exerciseGoals: List<ExerciseGoalResponse>?,
) {
    companion object {
        @JvmStatic
        fun of(item: ExerciseItem, areas: List<ExerciseArea>?, goals: List<ExerciseGoal>?): ExerciseItemResponse {
            return ExerciseItemResponse(
                id = item.id,
                imageUri = item.imageUri,
                videoUri = item.videoUri,
                exerciseName = item.exerciseName,
                exerciseAreas = areas?.map { area ->
                    ExerciseAreaResponse.from(area)
                },
                exerciseGoals = goals?.map { goal ->
                    ExerciseGoalResponse.from(goal)
                },
            )
        }
    }
}
