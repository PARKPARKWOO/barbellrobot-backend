package com.example.core.exercise.application.dto

import com.example.core.exercise.model.ExerciseArea
import com.example.core.exercise.model.ExerciseGoal
import com.example.core.exercise.model.ExerciseItem
import com.example.core.exercise.model.ItemYoutubeInfo
import com.querydsl.core.annotations.QueryProjection

data class QueryItemDto
    @QueryProjection
    constructor(
        val item: ExerciseItem,
        val goals: List<ExerciseGoal>,
        val areas: List<ExerciseArea>,
        val count: Int,
        val itemYoutubeInfo: List<ItemYoutubeInfo>
    )
