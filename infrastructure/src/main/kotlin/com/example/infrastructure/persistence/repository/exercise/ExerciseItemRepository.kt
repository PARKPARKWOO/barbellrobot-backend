package com.example.infrastructure.persistence.repository.exercise

import com.example.core.exercise.dto.QueryItemDto
import com.example.infrastructure.persistence.entity.exercise.ExerciseItemEntity
import com.querydsl.core.Tuple
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface ExerciseItemRepository : JpaRepository<ExerciseItemEntity, Long>, ExerciseItemQueryRepository

interface ExerciseItemQueryRepository {
    fun findItemAndAreaAndGoal(id: Long): QueryItemDto?

    fun findItemDetailAll(): List<QueryItemDto>

    fun findInIds(ids: List<Long>): List<QueryItemDto>
}

@Repository
class ExerciseItemQueryRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : ExerciseItemQueryRepository {
    override fun findItemAndAreaAndGoal(id: Long): QueryItemDto? {
        val exerciseItem: ExerciseItemEntity? = jpaQueryFactory
            .selectFrom(exerciseItemEntity)
            .where(exerciseItemEntity.id.eq(id))
            .fetchOne()

        val youtubeInfo: List<ItemYoutubeInfo> = jpaQueryFactory
            .selectFrom(itemYoutubeInfo)
            .where(itemYoutubeInfo.item.id.eq(id))
            .fetch()

        val exerciseAreas: List<ExerciseAreaEntity> = jpaQueryFactory
            .selectFrom(exerciseAreaEntity)
            .join(itemAreaRelationshipEntity)
            .on(itemAreaRelationshipEntity.exerciseAreaId.eq(exerciseAreaEntity.id))
            .where(itemAreaRelationshipEntity.exerciseItemId.eq(id))
            .fetch()

        val exerciseGoals: List<ExerciseGoalEntity> = jpaQueryFactory
            .selectFrom(exerciseGoalEntity)
            .join(itemGoalRelationshipEntity)
            .on(itemGoalRelationshipEntity.exerciseGoalId.eq(exerciseGoalEntity.id))
            .where(itemGoalRelationshipEntity.exerciseItemId.eq(id))
            .fetch()
        val count: Int = jpaQueryFactory
            .select(exerciseHistoryEntity.itemId.count())
            .from(exerciseHistoryEntity)
            .groupBy(exerciseHistoryEntity.itemId)
            .fetchOne()?.toInt() ?: 0

        return exerciseItem?.let { item ->
            QueryItemDto(
                item = item.toDomain(),
                goals = exerciseGoals.map { goal ->
                    goal.toDomain()
                },
                areas = exerciseAreas.map { area ->
                    area.toDomain()
                },
                count = count,
                itemYoutubeInfo = youtubeInfo.map { it.toDomain() },
            )
        }
    }

//    override fun findItemDetailAll(): List<QueryItemDto> {
//        val exerciseItems: List<ExerciseItemEntity> = jpaQueryFactory
//            .selectFrom(exerciseItemEntity)
//            .fetch()
//
//        val youtubeInfo: List<ItemYoutubeInfo> = jpaQueryFactory
//            .selectFrom(itemYoutubeInfo)
//            .where(itemYoutubeInfo.item.id.`in`(exerciseItems.map { it.id }))
//            .fetch()
//
//        val exerciseAreas: List<ExerciseAreaEntity> = jpaQueryFactory
//            .selectFrom(exerciseAreaEntity)
//            .fetch()
//
//        val exerciseGoals: List<ExerciseGoalEntity> = jpaQueryFactory
//            .selectFrom(exerciseGoalEntity)
//            .fetch()
//
//        val itemAreaRelationships: List<ItemAreaRelationshipEntity> = jpaQueryFactory
//            .selectFrom(itemAreaRelationshipEntity)
//            .fetch()
//
//        val itemGoalRelationships: List<ItemGoalRelationshipEntity> = jpaQueryFactory
//            .selectFrom(itemGoalRelationshipEntity)
//            .fetch()
//
//        val historyCounts: List<Tuple> = jpaQueryFactory
//            .select(exerciseHistoryEntity.itemId, exerciseHistoryEntity.itemId.count())
//            .from(exerciseHistoryEntity)
//            .groupBy(exerciseHistoryEntity.itemId)
//            .fetch()
//
//        val historyCountMap: Map<Long, Long> = historyCounts.associate {
//            it[exerciseHistoryEntity.itemId]!! to it[exerciseHistoryEntity.itemId.count()]!!
//        }
//
//        return getItemQueryDto(
//            exerciseItems = exerciseItems,
//            exerciseAreas = exerciseAreas,
//            exerciseGoals = exerciseGoals,
//            itemAreaRelationships = itemAreaRelationships,
//            itemGoalRelationships = itemGoalRelationships,
//            historyCountMap = historyCountMap,
//            itemYoutubeInfo = youtubeInfo,
//        )
//    }

    override fun findItemDetailAll(): List<QueryItemDto> {
        val result = jpaQueryFactory
            .select(
                exerciseItemEntity,
                itemYoutubeInfo,
                exerciseAreaEntity,
                exerciseGoalEntity,
                exerciseHistoryEntity.itemId.count(),
            )
            .from(exerciseItemEntity)
            .leftJoin(itemYoutubeInfo).on(itemYoutubeInfo.item.id.eq(exerciseItemEntity.id))
            .leftJoin(itemAreaRelationshipEntity)
            .on(itemAreaRelationshipEntity.exerciseItemId.eq(exerciseItemEntity.id))
            .leftJoin(exerciseAreaEntity).on(exerciseAreaEntity.id.eq(itemAreaRelationshipEntity.exerciseAreaId))
            .leftJoin(itemGoalRelationshipEntity)
            .on(itemGoalRelationshipEntity.exerciseItemId.eq(exerciseItemEntity.id))
            .leftJoin(exerciseGoalEntity).on(exerciseGoalEntity.id.eq(itemGoalRelationshipEntity.exerciseGoalId))
            .leftJoin(exerciseHistoryEntity).on(exerciseHistoryEntity.itemId.eq(exerciseItemEntity.id))
            .groupBy(exerciseItemEntity.id, itemYoutubeInfo.id, exerciseAreaEntity.id, exerciseGoalEntity.id)
            .fetch()

        return result.groupBy { it.get(exerciseItemEntity) }
            .map { (item, group) ->
                QueryItemDto(
                    item = item!!.toDomain(),
                    goals = group.mapNotNull { it.get(exerciseGoalEntity)?.toDomain() }.distinct(),
                    areas = group.mapNotNull { it.get(exerciseAreaEntity)?.toDomain() }.distinct(),
                    count = group.firstOrNull()?.get(exerciseHistoryEntity.itemId.count())?.toInt() ?: 0,
                    itemYoutubeInfo = group.mapNotNull { it.get(itemYoutubeInfo)?.toDomain() }.distinct(),
                )
            }
    }

    override fun findInIds(ids: List<Long>): List<QueryItemDto> {
        val exerciseItems: List<ExerciseItemEntity> = jpaQueryFactory
            .selectFrom(exerciseItemEntity)
            .where(exerciseItemEntity.id.`in`(ids))
            .fetch()

        val youtubeInfo: List<ItemYoutubeInfo> = jpaQueryFactory
            .selectFrom(itemYoutubeInfo)
            .where(itemYoutubeInfo.item.id.`in`(ids))
            .fetch()

        val itemAreaRelationships: List<ItemAreaRelationshipEntity> = jpaQueryFactory
            .selectFrom(itemAreaRelationshipEntity)
            .where(itemAreaRelationshipEntity.exerciseItemId.`in`(ids))
            .fetch()

        val itemGoalRelationships: List<ItemGoalRelationshipEntity> = jpaQueryFactory
            .selectFrom(itemGoalRelationshipEntity)
            .where(itemGoalRelationshipEntity.exerciseItemId.`in`(ids))
            .fetch()

        val exerciseAreas: List<ExerciseAreaEntity> = jpaQueryFactory
            .selectFrom(exerciseAreaEntity)
            .where(exerciseAreaEntity.id.`in`(itemAreaRelationships.map { it.exerciseAreaId }))
            .fetch()

        val exerciseGoals: List<ExerciseGoalEntity> = jpaQueryFactory
            .selectFrom(exerciseGoalEntity)
            .where(exerciseGoalEntity.id.`in`(itemGoalRelationships.map { it.exerciseGoalId }))
            .fetch()

        val historyCounts: List<Tuple> = jpaQueryFactory
            .select(exerciseHistoryEntity.itemId, exerciseHistoryEntity.itemId.count())
            .from(exerciseHistoryEntity)
            .where(exerciseHistoryEntity.itemId.`in`(ids))
            .groupBy(exerciseHistoryEntity.itemId)
            .fetch()

        val historyCountMap: Map<Long, Long> = getHistoryCountMap(historyCounts)

        return getItemQueryDto(
            exerciseItems = exerciseItems,
            exerciseAreas = exerciseAreas,
            exerciseGoals = exerciseGoals,
            itemAreaRelationships = itemAreaRelationships,
            itemGoalRelationships = itemGoalRelationships,
            historyCountMap = historyCountMap,
            itemYoutubeInfo = youtubeInfo,
        )
    }

    private fun getHistoryCountMap(historyCounts: List<Tuple>): Map<Long, Long> =
        historyCounts.associate {
            it[exerciseHistoryEntity.itemId]!! to it[exerciseHistoryEntity.itemId.count()]!!
        }

    private fun getItemQueryDto(
        exerciseItems: List<ExerciseItemEntity>,
        itemAreaRelationships: List<ItemAreaRelationshipEntity>,
        exerciseAreas: List<ExerciseAreaEntity>,
        itemGoalRelationships: List<ItemGoalRelationshipEntity>,
        exerciseGoals: List<ExerciseGoalEntity>,
        historyCountMap: Map<Long, Long>,
        itemYoutubeInfo: List<ItemYoutubeInfo>,
    ): List<QueryItemDto> = exerciseItems.map { item ->
        val areas = itemAreaRelationships
            .filter { it.exerciseItemId == item.id }
            .mapNotNull { relationship ->
                exerciseAreas.find { it.id == relationship.exerciseAreaId }
            }
        val goals = itemGoalRelationships
            .filter { it.exerciseItemId == item.id }
            .mapNotNull { relationship ->
                exerciseGoals.find { it.id == relationship.exerciseGoalId }
            }
        val youtube = itemYoutubeInfo.filter { it.item.id == it.id }
        val count = historyCountMap[item.id]?.toInt() ?: 0
        QueryItemDto(
            item = item.toDomain(),
            goals = goals.map { it.toDomain() },
            areas = areas.map { it.toDomain() },
            count = count,
            itemYoutubeInfo = youtube.map { it.toDomain() },
        )
    }
}
