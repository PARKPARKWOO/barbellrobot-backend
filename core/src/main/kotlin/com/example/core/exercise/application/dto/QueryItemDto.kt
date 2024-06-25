package com.example.core.exercise.application.dto

import com.example.domain.exercise.ExerciseArea
import com.example.domain.exercise.ExerciseGoal
import com.example.domain.exercise.ExerciseItem
import com.example.domain.exercise.ItemYoutubeInfo
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
