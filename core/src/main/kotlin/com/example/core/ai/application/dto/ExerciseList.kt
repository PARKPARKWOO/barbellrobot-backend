package com.example.core.ai.application.dto

import com.example.core.exercise.model.ExerciseItem
import com.fasterxml.jackson.annotation.JsonPropertyDescription

data class ExerciseList(
    @JsonPropertyDescription("Identification number")
    val id: Long? = null,
    @JsonPropertyDescription("Exercise name")
    val exerciseName: String? = null,
) {
    companion object {
        fun fromDomain(domain: ExerciseItem): ExerciseList = ExerciseList(
            id = domain.id,
            exerciseName = domain.exerciseName,
        )
    }
}
