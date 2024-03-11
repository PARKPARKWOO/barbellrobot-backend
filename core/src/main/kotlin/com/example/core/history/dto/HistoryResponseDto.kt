package com.example.core.history.dto

import com.querydsl.core.annotations.QueryProjection
import java.time.LocalDate
import java.util.UUID

data class HistoryResponseDto(
    val userHistoryResponseDto: UserHistoryResponseDto?,
    val exerciseHistoryResponseDto: List<ExerciseHistoryResponseDto>?,
)

data class UserHistoryResponseDto
    @QueryProjection
    constructor(
        val id: UUID,
        val today: LocalDate,
        val attendance: Boolean,
        val breakfastImageIds: List<Long>?,
        val breakfastFoods: List<String>?,
        val lunchImageIds: List<Long>?,
        val lunchFoods: List<String>?,
        val dinnerImageIds: List<Long>?,
        val dinnerFoods: List<String>?,
        val todayImageIds: List<Long>?,
        val todayVideo: List<Long>?,
    )

data class ExerciseHistoryResponseDto
    @QueryProjection
    constructor(
        val id: Long,
        val itemId: Long,
        val weight: Double,
        val exerciseSet: Int,
        val userHistoryId: UUID,
        val createdAt: LocalDate,
    )
