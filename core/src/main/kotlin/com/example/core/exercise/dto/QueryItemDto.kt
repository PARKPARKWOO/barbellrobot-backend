package com.example.core.exercise.dto

import com.example.core.exercise.model.ExerciseArea
import com.example.core.exercise.model.ExerciseGoal
import com.example.core.exercise.model.ExerciseItem
import com.example.core.exercise.model.ItemYoutubeInfo

data class QueryItemDto
constructor(
    val item: ExerciseItem,
    val goals: List<ExerciseGoal>,
    val areas: List<ExerciseArea>,
    val count: Int,
    val itemYoutubeInfo: List<ItemYoutubeInfo>,
)
