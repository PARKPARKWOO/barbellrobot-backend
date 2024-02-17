package com.example.core.exercise.adapter.out.persistence.repository

import com.example.core.exercise.adapter.out.persistence.entity.relationship.ItemGoalRelationshipEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ItemGoalRelationshipRepository : JpaRepository<ItemGoalRelationshipEntity, Long> {
    fun findByExerciseItemId(exerciseItemId: Long): List<ItemGoalRelationshipEntity>?
}
