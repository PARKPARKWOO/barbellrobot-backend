package com.example.core.exercise.adapter.out.persistence.repository

import com.example.core.exercise.adapter.out.persistence.entity.ExerciseAreaEntity
import com.example.core.exercise.adapter.out.persistence.entity.QExerciseAreaEntity.exerciseAreaEntity
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface ExerciseAreaRepository : JpaRepository<ExerciseAreaEntity, Long>, ExerciseAreaQueryRepository

interface ExerciseAreaQueryRepository {
    fun queryIdsIn(ids: List<Long>): List<ExerciseAreaEntity>?
}

@Repository
class ExerciseAreaQueryRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : ExerciseAreaQueryRepository {
    override fun queryIdsIn(ids: List<Long>): List<ExerciseAreaEntity>? {
        return jpaQueryFactory.selectFrom(exerciseAreaEntity)
            .where(exerciseAreaEntity.id.`in`(ids))
            .fetch()
    }
}
