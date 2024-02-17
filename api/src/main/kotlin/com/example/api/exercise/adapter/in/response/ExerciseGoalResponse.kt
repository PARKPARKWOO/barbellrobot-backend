package com.example.api.exercise.adapter.`in`.response

import com.example.domain.exercise.ExerciseGoal

data class ExerciseGoalResponse(
    val id: Long,
    val goal: String,
) {
    companion object {
        @JvmStatic
        fun from(domain: ExerciseGoal): ExerciseGoalResponse {
            return ExerciseGoalResponse(
                id = domain.id,
                goal = domain.goal,
            )
        }
    }
}
