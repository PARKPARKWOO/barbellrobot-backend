package com.example.core.exercise.adapter.out.persistence.repository

import com.example.core.exercise.adapter.out.persistence.entity.ExerciseAreaEntity
import com.example.core.exercise.adapter.out.persistence.entity.ExerciseGoalEntity
import com.example.core.exercise.adapter.out.persistence.entity.ExerciseItemEntity
import com.example.core.exercise.adapter.out.persistence.entity.QExerciseAreaEntity.exerciseAreaEntity
import com.example.core.exercise.adapter.out.persistence.entity.QExerciseGoalEntity.exerciseGoalEntity
import com.example.core.exercise.adapter.out.persistence.entity.QExerciseItemEntity.exerciseItemEntity
import com.example.core.exercise.adapter.out.persistence.entity.relationship.QItemAreaRelationshipEntity.itemAreaRelationshipEntity
import com.example.core.exercise.adapter.out.persistence.entity.relationship.QItemGoalRelationshipEntity.itemGoalRelationshipEntity
import com.example.core.exercise.application.dto.QueryItemDto
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface ExerciseItemRepository : JpaRepository<ExerciseItemEntity, Long>, ExerciseItemQueryRepository

interface ExerciseItemQueryRepository {
    fun findItemAndAreaAndGoal(id: Long): QueryItemDto?
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
            .join(itemAreaRelationshipEntity).on(itemAreaRelationshipEntity.exerciseAreaId.eq(exerciseAreaEntity.id))
            .where(itemAreaRelationshipEntity.exerciseItemId.eq(id))
            .fetch()

        val exerciseGoals: List<ExerciseGoalEntity> = jpaQueryFactory
            .selectFrom(exerciseGoalEntity)
            .join(itemGoalRelationshipEntity).on(itemGoalRelationshipEntity.exerciseGoalId.eq(exerciseGoalEntity.id))
            .where(itemGoalRelationshipEntity.exerciseItemId.eq(id))
            .fetch()

        return exerciseItem?.let { item ->
            QueryItemDto(
                item = item.toDomain(),
                goals = exerciseGoals.map { goal ->
                    goal.toDomain()
                },
                areas = exerciseAreas.map { area ->
                    area.toDomain()
                },
            )
        }
    }
}
