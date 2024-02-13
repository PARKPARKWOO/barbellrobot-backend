package com.example.core.exercise.adapter.out.persistence.repository

import com.example.core.exercise.adapter.out.persistence.entity.ExerciseItemEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ExerciseItemRepository : JpaRepository<ExerciseItemEntity, Long> {
    fun findByExerciseAreasContains(exerciseAreaId: Long): List<ExerciseItemEntity>?
}

interface ExerciseItemQueryRepository
