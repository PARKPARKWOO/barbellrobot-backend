package com.example.infrastructure.persistence.repository.exercise

import com.example.infrastructure.persistence.entity.exercise.ExerciseAreaEntity
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
