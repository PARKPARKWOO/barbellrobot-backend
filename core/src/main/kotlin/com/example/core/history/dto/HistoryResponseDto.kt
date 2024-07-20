package com.example.core.history.dto

import com.example.core.history.model.Diet
import java.time.LocalDate
import java.util.UUID

data class HistoryResponseDto(
    val userHistoryResponseDto: UserHistoryResponseDto?,
    val exerciseHistoryResponseDto: List<ExerciseHistoryResponseDto>?,
)

data class UserHistoryResponseDto(
    val id: UUID,
    val today: LocalDate,
    val attendance: Boolean,
    val dietFoodDtos: List<DietFoodQueryDto>,
    val dietImageDtos: List<DietImageQueryDto>,
    val todayImages: List<String>?,
    val todayVideo: List<String>?,
)

data class UserHistoryQueryDto(
    val id: UUID,
    val today: LocalDate,
    val attendance: Boolean,
    var todayImages: List<UserHistoryImageQueryDto>?,
    var todayVideo: List<UserHistoryVideoQueryDto>?,
)

data class UserHistoryImageQueryDto(
    val imageUrl: String,
)

data class UserHistoryVideoQueryDto(
    val videoUrl: String,
)

data class DietFoodQueryDto(
    val historyId: UUID,
    val food: String,
    val type: Diet,
)

data class DietImageQueryDto(
    val historyId: UUID,
    val imageUrl: String,
    val type: Diet,
)

data class ExerciseHistoryResponseDto(
    val id: Long,
    val itemId: Long,
    val weight: Double,
    val exerciseSet: Int,
    val userHistoryId: UUID,
    val createdAt: LocalDate,
)
