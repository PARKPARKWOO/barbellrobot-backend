package com.example.core.exercise.application.dto

import com.example.domain.exercise.ExerciseArea
import com.example.domain.exercise.ExerciseGoal
import com.example.domain.exercise.ExerciseItem
import com.querydsl.core.annotations.QueryProjection
import org.springframework.data.jpa.repository.Query

data class QueryItemDto
    @QueryProjection
    constructor(
        val item: ExerciseItem,
        val goals: List<ExerciseGoal>?,
        val areas: List<ExerciseArea>?,
    )
