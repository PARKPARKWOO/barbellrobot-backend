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
        val todayImages: List<String>?,
        val todayVideo: List<String>?,
    )

data class UserHistoryQueryDto
    @QueryProjection
    constructor(
        val id: UUID,
        val today: LocalDate,
        val attendance: Boolean,
        var todayImages: List<UserHistoryImageQueryDto>?,
        var todayVideo: List<UserHistoryVideoQueryDto>?,
    )
    {
        fun addImage(dto: List<UserHistoryImageQueryDto>?) {
            todayImages = dto
        }

        fun addVideo(dto: List<UserHistoryVideoQueryDto>?) {
            todayVideo = dto
        }
    }

data class UserHistoryImageQueryDto
    @QueryProjection
    constructor(
        val imageUrl: String,
    )

data class UserHistoryVideoQueryDto
    @QueryProjection
    constructor(
        val videoUrl: String,
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
