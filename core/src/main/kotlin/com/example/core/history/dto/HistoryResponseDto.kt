package com.example.core.history.dto

import com.example.domain.history.Diet
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
        val dietFoodDtos: List<DietFoodQueryDto>,
        val dietImageDtos: List<DietImageQueryDto>,
        val todayImageIds: List<String>?,
        val todayVideo: List<String>?,
    )

data class UserHistoryQueryDto
    @QueryProjection
    constructor(
        val id: UUID,
        val today: LocalDate,
        val attendance: Boolean,
        var todayImageIds: List<Long>?,
        var todayVideoIds: List<Long>?,
    )
data class DietFoodQueryDto
    @QueryProjection
    constructor(
        val historyId: UUID,
        val food: String,
        val type: Diet,
    )

data class DietImageQueryDto
    @QueryProjection
    constructor(
        val historyId: UUID,
        val imageUrl: String,
        val type: Diet,
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
