package com.example.core.exercise.adapter.out.persistence.repository

import com.example.core.exercise.adapter.out.persistence.entity.relationship.ItemAreaRelationshipEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ItemAreaRelationshipRepository : JpaRepository<ItemAreaRelationshipEntity, Long> {
    fun findByExerciseItemId(exerciseItemId: Long): List<ItemAreaRelationshipEntity>?
}
