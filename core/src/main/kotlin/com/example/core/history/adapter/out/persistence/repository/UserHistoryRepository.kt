package com.example.core.history.adapter.out.persistence.repository

import com.example.core.history.adapter.out.persistence.entity.QExerciseHistoryEntity.exerciseHistoryEntity
import com.example.core.history.adapter.out.persistence.entity.QUserHistoryEntity
import com.example.core.history.adapter.out.persistence.entity.QUserHistoryEntity.userHistoryEntity
import com.example.core.history.adapter.out.persistence.entity.UserHistoryEntity
import com.example.core.history.dto.HistoryResponseDto
import com.example.core.history.dto.QExerciseHistoryResponseDto
import com.example.core.history.dto.QUserHistoryResponseDto
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.UUID

interface UserHistoryRepository : JpaRepository<UserHistoryEntity, UUID>, UserHistoryQueryRepository

interface UserHistoryQueryRepository {
    fun getHistoryBetween(userId: UUID, startDate: LocalDate, endDate: LocalDate): List<HistoryResponseDto>?
}

@Repository
class UserHistoryQueryRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : UserHistoryQueryRepository {
    override fun getHistoryBetween(
        userId: UUID,
        startDate: LocalDate,
        endDate: LocalDate,
    ): List<HistoryResponseDto>? {
        val userHistoryDto = jpaQueryFactory.select(
            QUserHistoryResponseDto(
                userHistoryEntity.id,
                userHistoryEntity.today,
                userHistoryEntity.attendance,
                userHistoryEntity.breakfastImageIds,
                userHistoryEntity.breakfastFoods,
                userHistoryEntity.lunchImageIds,
                userHistoryEntity.lunchFoods,
                userHistoryEntity.dinnerImageIds,
                userHistoryEntity.dinnerFoods,
                userHistoryEntity.todayImageIds,
                userHistoryEntity.todayVideo,
            ),
        )
            .from(userHistoryEntity)
            .where(betweenDate(startDate, endDate).and(userHistoryEntity.userId.eq(userId)))
            .fetch()
        val exerciseHistoryDto = jpaQueryFactory.select(
            QExerciseHistoryResponseDto(
                exerciseHistoryEntity.id,
                exerciseHistoryEntity.itemId,
                exerciseHistoryEntity.weight,
                exerciseHistoryEntity.exerciseSet,
                exerciseHistoryEntity.userHistoryId,
                exerciseHistoryEntity.createdAt,
            ),
        ).from(exerciseHistoryEntity)
            .where(exerciseHistoryEntity.userHistoryId.`in`(userHistoryDto.map { it.id }))
            .fetch()
        return userHistoryDto.map { dto ->
            HistoryResponseDto(
                userHistoryResponseDto = dto,
                exerciseHistoryResponseDto = exerciseHistoryDto.filter {
                    it.userHistoryId == dto.id
                },
            )
        }
    }

    private fun betweenDate(startDate: LocalDate, endDate: LocalDate) =
        userHistoryEntity.today.between(startDate, endDate)
}
