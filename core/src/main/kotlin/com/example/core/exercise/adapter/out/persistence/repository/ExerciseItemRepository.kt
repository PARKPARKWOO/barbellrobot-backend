package com.example.core.exercise.adapter.out.persistence.repository

import com.example.core.exercise.adapter.out.persistence.entity.ExerciseAreaEntity
import com.example.core.exercise.adapter.out.persistence.entity.ExerciseGoalEntity
import com.example.core.exercise.adapter.out.persistence.entity.ExerciseItemEntity
import com.example.core.exercise.adapter.out.persistence.entity.QExerciseAreaEntity.exerciseAreaEntity
import com.example.core.exercise.adapter.out.persistence.entity.QExerciseGoalEntity.exerciseGoalEntity
import com.example.core.exercise.adapter.out.persistence.entity.QExerciseItemEntity.exerciseItemEntity
import com.example.core.exercise.adapter.out.persistence.entity.relationship.ItemAreaRelationshipEntity
import com.example.core.exercise.adapter.out.persistence.entity.relationship.ItemGoalRelationshipEntity
import com.example.core.exercise.adapter.out.persistence.entity.relationship.QItemAreaRelationshipEntity.itemAreaRelationshipEntity
import com.example.core.exercise.adapter.out.persistence.entity.relationship.QItemGoalRelationshipEntity.itemGoalRelationshipEntity
import com.example.core.exercise.application.dto.QueryItemDto
import com.example.core.history.adapter.out.persistence.entity.QExerciseHistoryEntity.exerciseHistoryEntity
import com.querydsl.core.Tuple
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface ExerciseItemRepository : JpaRepository<ExerciseItemEntity, Long>, ExerciseItemQueryRepository

interface ExerciseItemQueryRepository {
    fun findItemAndAreaAndGoal(id: Long): QueryItemDto?

    fun findItemDetailAll(): List<QueryItemDto>
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
            )
        }
    }

    override fun findItemDetailAll(): List<QueryItemDto> {
        val exerciseItems: List<ExerciseItemEntity> = jpaQueryFactory
            .selectFrom(exerciseItemEntity)
            .fetch()

        val exerciseAreas: List<ExerciseAreaEntity> = jpaQueryFactory
            .selectFrom(exerciseAreaEntity)
            .fetch()

        val exerciseGoals: List<ExerciseGoalEntity> = jpaQueryFactory
            .selectFrom(exerciseGoalEntity)
            .fetch()

        val itemAreaRelationships: List<ItemAreaRelationshipEntity> = jpaQueryFactory
            .selectFrom(itemAreaRelationshipEntity)
            .fetch()

        val itemGoalRelationships: List<ItemGoalRelationshipEntity> = jpaQueryFactory
            .selectFrom(itemGoalRelationshipEntity)
            .fetch()

        val historyCounts: List<Tuple> = jpaQueryFactory
            .select(exerciseHistoryEntity.itemId, exerciseHistoryEntity.itemId.count())
            .from(exerciseHistoryEntity)
            .groupBy(exerciseHistoryEntity.itemId)
            .fetch()

        val historyCountMap: Map<Long, Long> = historyCounts.associate {
            it[exerciseHistoryEntity.itemId]!! to it[exerciseHistoryEntity.itemId.count()]!!
        }

        return exerciseItems.map { item ->
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
            val count = historyCountMap[item.id]?.toInt() ?: 0
            QueryItemDto(
                item = item.toDomain(),
                goals = goals.map { it.toDomain() },
                areas = areas.map { it.toDomain() },
                count = count,
            )
        }
    }
}
