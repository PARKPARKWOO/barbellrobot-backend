package com.example.core.history.adapter.out.persistence.repository

import com.example.core.history.adapter.out.persistence.entity.QDietFoodEntity.dietFoodEntity
import com.example.core.history.adapter.out.persistence.entity.QDietImageEntity.dietImageEntity
import com.example.core.history.adapter.out.persistence.entity.QExerciseHistoryEntity.exerciseHistoryEntity
import com.example.core.history.adapter.out.persistence.entity.QUserHistoryEntity.userHistoryEntity
import com.example.core.history.adapter.out.persistence.entity.QUserHistoryImageEntity.userHistoryImageEntity
import com.example.core.history.adapter.out.persistence.entity.QUserHistoryVideoEntity.userHistoryVideoEntity
import com.example.core.history.adapter.out.persistence.entity.UserHistoryEntity
import com.example.core.history.dto.HistoryResponseDto
import com.example.core.history.dto.QDietFoodQueryDto
import com.example.core.history.dto.QDietImageQueryDto
import com.example.core.history.dto.QExerciseHistoryResponseDto
import com.example.core.history.dto.UserHistoryResponseDto
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.UUID

interface UserHistoryRepository : JpaRepository<UserHistoryEntity, UUID>, UserHistoryQueryRepository

interface UserHistoryQueryRepository {
    fun getHistoryBetween(userId: UUID, startDate: LocalDate, endDate: LocalDate): List<HistoryResponseDto>?

    fun findUserHistoryToday(userId: UUID, today: LocalDate): UserHistoryEntity?
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
// Fetch UserHistories
        val userHistories: List<UserHistoryEntity> = jpaQueryFactory
            .selectFrom(userHistoryEntity)
            .where(
                betweenDate(startDate, endDate).and(userHistoryEntity.userId.eq(userId)),
            )
            .fetch()

// Fetch diet foods
        val dietFoods = jpaQueryFactory.select(
            QDietFoodQueryDto(
                userHistoryEntity.id,
                dietFoodEntity.food,
                dietFoodEntity.type,
            ),
        ).from(dietFoodEntity)
            .leftJoin(userHistoryEntity).on(userHistoryEntity.id.eq(dietFoodEntity.historyId))
            .where(userHistoryEntity.userId.eq(userId))
            .fetch()
// Fetch diet images
        val dietImages = jpaQueryFactory.select(
            QDietImageQueryDto(
                userHistoryEntity.id,
                dietImageEntity.image,
                dietImageEntity.type,
            ),
        ).from(dietImageEntity)
            .leftJoin(userHistoryEntity).on(userHistoryEntity.id.eq(dietImageEntity.historyId))
            .where(userHistoryEntity.userId.eq(userId))
            .fetch()
// Fetch user history images
        val userHistoryImages = jpaQueryFactory.select(userHistoryImageEntity)
            .from(userHistoryImageEntity)
            .where(userHistoryImageEntity.userHistoryEntity.id.`in`(userHistories.map { it.id }))
            .fetch()
// Fetch user history videos
        val userHistoryVideos = jpaQueryFactory.selectFrom(
            userHistoryVideoEntity,
        ).from(userHistoryVideoEntity)
            .where(userHistoryVideoEntity.userHistoryEntity.id.`in`(userHistories.map { it.id }))
            .fetch()
// Fetch exercise histories
        val exerciseHistories = jpaQueryFactory.select(
            QExerciseHistoryResponseDto(
                exerciseHistoryEntity.id,
                exerciseHistoryEntity.itemId,
                exerciseHistoryEntity.weight,
                exerciseHistoryEntity.exerciseSet,
                exerciseHistoryEntity.userHistoryId,
                exerciseHistoryEntity.createdAt,
            ),
        ).from(exerciseHistoryEntity)
            .where(exerciseHistoryEntity.userHistoryId.`in`(userHistories.map { it.id }))
            .fetch()
// Group data by userHistoryId
        val dietFoodMap = dietFoods.groupBy { it.historyId }
        val dietImageMap = dietImages.groupBy { it.historyId }
        val userHistoryImageMap = userHistoryImages.groupBy { it.userHistoryEntity.id }
        val userHistoryVideoMap = userHistoryVideos.groupBy { it.userHistoryEntity.id }
        val exerciseHistoryMap = exerciseHistories.groupBy { it.userHistoryId }
// Map to UserHistoryResponseDto
        val userHistoryResponseDtos = userHistories.map { userHistory ->
            UserHistoryResponseDto(
                id = userHistory.id,
                today = userHistory.today,
                attendance = userHistory.attendance,
                dietFoodDtos = dietFoodMap[userHistory.id] ?: emptyList(),
                dietImageDtos = dietImageMap[userHistory.id] ?: emptyList(),
                todayImages = userHistoryImageMap[userHistory.id]?.map { it.imageUrl } ?: emptyList(),
                todayVideo = userHistoryVideoMap[userHistory.id]?.map { it.videoUrl } ?: emptyList(),
            )
        }
// Return final result with exercise histories
        return userHistoryResponseDtos.map { dto ->
            HistoryResponseDto(
                userHistoryResponseDto = dto,
                exerciseHistoryResponseDto = exerciseHistoryMap[dto.id] ?: emptyList(),
            )
        }
    }

    override fun findUserHistoryToday(userId: UUID, today: LocalDate): UserHistoryEntity? {
        return jpaQueryFactory.selectFrom(userHistoryEntity)
            .where(
                userHistoryEntity.userId.eq(userId)
                    .and(userHistoryEntity.today.eq(today)),
            )
            .fetchOne()
    }

    private fun betweenDate(startDate: LocalDate, endDate: LocalDate) =
        userHistoryEntity.today.goe(startDate).and(userHistoryEntity.today.lt(endDate.plusDays(1)))
}
