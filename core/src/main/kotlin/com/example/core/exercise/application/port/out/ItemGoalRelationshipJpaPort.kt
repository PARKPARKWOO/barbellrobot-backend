package com.example.core.exercise.application.port.out

import com.example.core.exercise.adapter.out.persistence.entity.relationship.ItemGoalRelationshipEntity
import com.example.core.exercise.application.port.command.AddItemGoalRelationCommand

interface ItemGoalRelationshipJpaPort {
    fun addRelationship(command: AddItemGoalRelationCommand)

    fun findByItemId(itemId: Long): List<ItemGoalRelationshipEntity>?

    fun deleteByGoalId(goalId: Long)

    fun deleteByItemId(itemId: Long)
}
