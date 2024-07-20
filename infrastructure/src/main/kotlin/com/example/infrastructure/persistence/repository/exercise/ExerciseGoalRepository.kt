package com.example.infrastructure.persistence.repository.exercise

import com.example.infrastructure.persistence.entity.exercise.ExerciseGoalEntity
import com.example.infrastructure.persistence.entity.exercise.QExerciseGoalEntity.exerciseGoalEntity
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface ExerciseGoalRepository : JpaRepository<ExerciseGoalEntity, Long>, ExerciseGoalQueryRepository

interface ExerciseGoalQueryRepository {
    fun queryIdsIn(ids: List<Long>): List<ExerciseGoalEntity>?
}

@Repository
class ExerciseGoalQueryRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : ExerciseGoalQueryRepository {
    override fun queryIdsIn(ids: List<Long>): List<ExerciseGoalEntity>? {
        return jpaQueryFactory.select(exerciseGoalEntity)
            .from(exerciseGoalEntity)
            .where(exerciseGoalEntity.id.`in`(ids)).fetch()
    }
}
