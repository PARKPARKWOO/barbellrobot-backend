package com.example.`in`.api.history.adapter.`in`.response

import com.example.core.history.dto.ExerciseHistoryResponseDto
import com.example.core.history.dto.UserHistoryResponseDto
import com.example.core.history.model.Diet
import java.time.LocalDate
import java.util.UUID

data class HistoryResponse(
    val userHistoryResponse: UserHistoryResponse?,
    val exerciseHistoryResponse: List<ExerciseHistoryResponse?>?,
)

data class UserHistoryResponse(
    val id: UUID,
    val today: LocalDate,
    val attendance: Boolean,
    val breakfastImageUri: List<String>?,
    val breakfastFoods: List<String>?,
    val lunchImageUri: List<String>?,
    val lunchFoods: List<String>?,
    val dinnerImageUri: List<String>?,
    val dinnerFoods: List<String>?,
    val todayImageUri: List<String>?,
    val todayVideoUri: List<String>?,
) {
    companion object {
        @JvmStatic
        fun from(dto: UserHistoryResponseDto?): UserHistoryResponse? {
            return dto?.let {
                val foodsByType = dto.dietFoodDtos.groupBy { it.type }
                val imagesByType = dto.dietImageDtos.groupBy { it.type }

                UserHistoryResponse(
                    id = dto.id,
                    today = dto.today,
                    attendance = dto.attendance,
                    breakfastFoods = foodsByType[Diet.BREAKFAST]?.map { it.food } ?: emptyList(),
                    breakfastImageUri = imagesByType[Diet.BREAKFAST]?.map { it.imageUrl } ?: emptyList(),
                    lunchFoods = foodsByType[Diet.LUNCH]?.map { it.food } ?: emptyList(),
                    lunchImageUri = imagesByType[Diet.LUNCH]?.map { it.imageUrl } ?: emptyList(),
                    dinnerFoods = foodsByType[Diet.DINNER]?.map { it.food } ?: emptyList(),
                    dinnerImageUri = imagesByType[Diet.DINNER]?.map { it.imageUrl } ?: emptyList(),
                    todayImageUri = dto.todayImages,
                    todayVideoUri = dto.todayVideo,
                )
            }
        }
    }
}

data class ExerciseHistoryResponse(
    val id: Long,
    val itemId: Long,
    val weight: Double,
    val exerciseSet: Int,
    val userHistoryId: UUID,
    val createdAt: LocalDate,
) {
    companion object {
        @JvmStatic
        fun from(dto: ExerciseHistoryResponseDto?): ExerciseHistoryResponse? {
            return dto?.let {
                ExerciseHistoryResponse(
                    id = dto.id,
                    itemId = dto.itemId,
                    weight = dto.weight,
                    exerciseSet = dto.exerciseSet,
                    userHistoryId = dto.userHistoryId,
                    createdAt = dto.createdAt,
                )
            }
        }
    }
}
