package com.example.core.exercise.application.out

import com.example.core.exercise.adapter.out.persistence.entity.relationship.ItemGoalRelationshipEntity
import com.example.core.exercise.application.out.command.AddItemGoalRelationCommand

interface ItemGoalRelationshipJpaPort {
    fun addRelationship(command: AddItemGoalRelationCommand)

    fun findByItemId(itemId: Long): List<ItemGoalRelationshipEntity>?

    fun removeRelationship(goalId: Long)
}
