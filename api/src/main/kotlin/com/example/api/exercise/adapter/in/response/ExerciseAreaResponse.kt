package com.example.api.exercise.adapter.`in`.response

import com.example.domain.exercise.ExerciseArea

data class ExerciseAreaResponse(
    val id: Long,
    val area: String,
) {
    companion object {
        @JvmStatic
        fun from(domain: ExerciseArea): ExerciseAreaResponse {
            return ExerciseAreaResponse(
                id = domain.id,
                area = domain.area,
            )
        }
    }
}
