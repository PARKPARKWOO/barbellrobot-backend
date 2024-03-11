package com.example.api.history.adapter.`in`.response

import com.example.core.history.dto.ExerciseHistoryResponseDto
import com.example.core.history.dto.UserHistoryResponseDto
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
    val breakfastImageIds: List<Long>?,
    val breakfastFoods: List<String>?,
    val lunchImageIds: List<Long>?,
    val lunchFoods: List<String>?,
    val dinnerImageIds: List<Long>?,
    val dinnerFoods: List<String>?,
    val todayImageIds: List<Long>?,
    val todayVideo: List<Long>?,
) {
    companion object {
        @JvmStatic
        fun from(dto: UserHistoryResponseDto?): UserHistoryResponse? {
            return dto?.let {
                UserHistoryResponse(
                    id = dto.id,
                    today = dto.today,
                    attendance = dto.attendance,
                    breakfastFoods = dto.breakfastFoods,
                    breakfastImageIds = dto.breakfastImageIds,
                    lunchFoods = dto.lunchFoods,
                    lunchImageIds = dto.lunchImageIds,
                    dinnerFoods = dto.dinnerFoods,
                    dinnerImageIds = dto.dinnerImageIds,
                    todayImageIds = dto.todayImageIds,
                    todayVideo = dto.todayVideo,
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
