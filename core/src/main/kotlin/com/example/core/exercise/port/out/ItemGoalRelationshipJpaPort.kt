package com.example.core.exercise.port.out

import com.example.core.exercise.model.relationship.ItemGoalRelationship
import com.example.core.exercise.port.command.AddItemGoalRelationCommand

interface ItemGoalRelationshipJpaPort {
    fun addRelationship(command: AddItemGoalRelationCommand)

    fun findByItemId(itemId: Long): List<ItemGoalRelationship>?

    fun deleteByGoalId(goalId: Long)

    fun deleteByItemId(itemId: Long)
}
