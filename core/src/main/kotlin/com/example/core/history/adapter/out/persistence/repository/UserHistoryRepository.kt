package com.example.core.history.adapter.out.persistence.repository

import com.example.core.history.adapter.out.persistence.entity.QDietFoodEntity.dietFoodEntity
import com.example.core.history.adapter.out.persistence.entity.QDietImageEntity.dietImageEntity
import com.example.core.history.adapter.out.persistence.entity.QExerciseHistoryEntity.exerciseHistoryEntity
import com.example.core.history.adapter.out.persistence.entity.QUserHistoryEntity.userHistoryEntity
import com.example.core.history.adapter.out.persistence.entity.UserHistoryEntity
import com.example.core.history.dto.HistoryResponseDto
import com.example.core.history.dto.QDietFoodQueryDto
import com.example.core.history.dto.QDietImageQueryDto
import com.example.core.history.dto.QExerciseHistoryResponseDto
import com.example.core.history.dto.QUserHistoryQueryDto
import com.example.core.history.dto.UserHistoryResponseDto
import com.example.core.multimedia.adapter.out.persistence.entity.QMultimediaEntity.multimediaEntity
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
        // startDay - endDay userHistory Query
//        val userQueryDto = jpaQueryFactory.select(
//            QUserHistoryQueryDto(
//                userHistoryEntity.id,
//                userHistoryEntity.today,
//                userHistoryEntity.attendance,
//                multimediaEntity.uri,
//                multimediaEntity.uri,
//            ),
//        )
//            .from(userHistoryEntity)
//            .leftJoin(multimediaEntity).on(multimediaEntity.id.eq(userHistoryEntity.todayImageIds)
//                .or(multimediaEntity.id.`in`(userHistoryEntity.todayVideo)))
//            .where(betweenDate(startDate, endDate).and(userHistoryEntity.userId.eq(userId)))
//            .fetch()
        val userQueryDto = jpaQueryFactory.select(
            QUserHistoryQueryDto(
                userHistoryEntity.id,
                userHistoryEntity.today,
                userHistoryEntity.attendance,
                userHistoryEntity.todayImageIds,
                userHistoryEntity.todayVideo,
            ),
        ).from(userHistoryEntity)
            .where(betweenDate(startDate, endDate).and(userHistoryEntity.userId.eq(userId)))
            .fetch()
        // userDietFood Query
        val dietFoodResult = jpaQueryFactory.select(
            QDietFoodQueryDto(
                userHistoryEntity.id,
                dietFoodEntity.food,
                dietFoodEntity.type,
            ),
        ).from(dietFoodEntity)
            .leftJoin(userHistoryEntity).on(userHistoryEntity.id.eq(dietFoodEntity.historyId))
            .where(userHistoryEntity.userId.eq(userId))
            .fetch()

        // userDietImage Query(get Image)
        val dietImageResult = jpaQueryFactory.select(
            QDietImageQueryDto(
                userHistoryEntity.id,
                multimediaEntity.uri,
                dietImageEntity.type,
            ),
        ).from(dietImageEntity)
            .leftJoin(multimediaEntity).on(dietImageEntity.multimediaId.eq(multimediaEntity.id))
            .leftJoin(userHistoryEntity).on(userHistoryEntity.id.eq(dietImageEntity.historyId))
            .where(userHistoryEntity.userId.eq(userId))
            .fetch()

        // userExerciseHistory Query
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
            .where(exerciseHistoryEntity.userHistoryId.`in`(userQueryDto.map { it.id }))
            .fetch()
        // queryDto -> responseDto
        val userHistoryResponseDtos = userQueryDto.map { userHistory ->
            // 해당 UserHistory에 대응하는 DietFood와 DietImage 찾기
            val correspondingDietFoods = dietFoodResult.filter { it.historyId == userHistory.id }
            val correspondingDietImages = dietImageResult.filter { it.historyId == userHistory.id }
            val imageUris = jpaQueryFactory.select(multimediaEntity.uri)
                .from(multimediaEntity)
                .where(multimediaEntity.id.`in`(userHistory.todayImageIds))
                .fetch()

            val videoUris = jpaQueryFactory.select(multimediaEntity.uri)
                .from(multimediaEntity)
                .where(multimediaEntity.id.`in`(userHistory.todayVideoIds))
                .fetch()

            // UserHistoryResponseDto 생성
            UserHistoryResponseDto(
                id = userHistory.id,
                today = userHistory.today,
                attendance = userHistory.attendance,
                dietFoodDtos = correspondingDietFoods,
                dietImageDtos = correspondingDietImages,
                todayImageIds = imageUris,
                todayVideo = videoUris,
            )
        }
        return userHistoryResponseDtos.map { dto ->
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
