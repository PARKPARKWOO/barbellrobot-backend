package com.example.core.exercise.adapter.out.persistence.repository

import com.example.core.exercise.adapter.out.persistence.entity.relationship.ItemGoalRelationshipEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ItemGoalRelationshipRepository : JpaRepository<ItemGoalRelationshipEntity, Long> {
    fun findByExerciseItemId(exerciseItemId: Long): List<ItemGoalRelationshipEntity>?

    @Query("delete from ItemGoalRelationshipEntity ig where ig.exerciseGoalId = :goalId")
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    fun deleteGoalId(@Param("goalId") goalId: Long)

    @Query("delete from ItemGoalRelationshipEntity ig where ig.exerciseItemId = :itemId")
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    fun deleteItemId(@Param("itemId") itemId: Long)
}
