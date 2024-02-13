package com.example.core.exercise.adapter.out.persistence.repository

import com.example.core.exercise.adapter.out.persistence.entity.ExerciseGoalEntity
import com.example.core.exercise.adapter.out.persistence.entity.QExerciseGoalEntity.exerciseGoalEntity
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface ExerciseGoalRepository : JpaRepository<ExerciseGoalEntity, Long>, ExerciseGoalQueryRepository

interface ExerciseGoalQueryRepository {
    fun findByIds(ids: List<Long>): List<ExerciseGoalEntity>?
}

@Repository
class ExerciseGoalQueryRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : ExerciseGoalQueryRepository {
    override fun findByIds(ids: List<Long>): List<ExerciseGoalEntity>? {
        return jpaQueryFactory.select(exerciseGoalEntity)
            .from(exerciseGoalEntity)
            .where(exerciseGoalEntity.id.`in`(ids)).fetch()
    }
}
