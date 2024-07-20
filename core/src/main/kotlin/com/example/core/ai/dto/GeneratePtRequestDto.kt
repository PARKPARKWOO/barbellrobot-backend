package com.example.core.ai.dto

import com.example.core.exercise.model.ExerciseItem

data class GeneratePtRequestDto(
    val exerciseList: List<ExerciseItem>? = null,
)
