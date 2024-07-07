package com.example.infrastructure.persistence.repository.exercise

import com.example.infrastructure.persistence.entity.exercise.ItemAreaRelationshipEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ItemAreaRelationshipRepository : JpaRepository<ItemAreaRelationshipEntity, Long> {
    fun findByExerciseItemId(exerciseItemId: Long): List<ItemAreaRelationshipEntity>?

    @Query("delete from ItemAreaRelationshipEntity ia where ia.exerciseItemId = :itemId")
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    fun deleteItemId(@Param("itemId") itemId: Long)

    @Query("delete from ItemAreaRelationshipEntity ia where ia.exerciseAreaId = :areaId")
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    fun deleteAreaId(@Param("areaId") areaId: Long)
}
