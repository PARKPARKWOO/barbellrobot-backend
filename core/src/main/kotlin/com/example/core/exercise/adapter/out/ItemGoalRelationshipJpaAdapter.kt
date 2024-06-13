package com.example.core.exercise.adapter.out

import com.example.core.exercise.adapter.out.persistence.entity.relationship.ItemGoalRelationshipEntity
import com.example.core.exercise.adapter.out.persistence.repository.ItemGoalRelationshipRepository
import com.example.core.exercise.application.port.command.AddItemGoalRelationCommand
import com.example.core.exercise.application.port.out.ItemGoalRelationshipJpaPort
import org.springframework.stereotype.Component

@Component
class ItemGoalRelationshipJpaAdapter(
    private val itemGoalRelationshipRepository: ItemGoalRelationshipRepository,
) : ItemGoalRelationshipJpaPort {
    override fun addRelationship(command: AddItemGoalRelationCommand) {
        val list = mutableListOf<ItemGoalRelationshipEntity>()
        for (goalId in command.goalIds) {
            val itemGoalRelationshipEntity = ItemGoalRelationshipEntity(
                exerciseGoalId = goalId,
                exerciseItemId = command.itemId,
            )
            list.add(itemGoalRelationshipEntity)
        }
        itemGoalRelationshipRepository.saveAll(list)
    }

    override fun findByItemId(itemId: Long): List<ItemGoalRelationshipEntity>? {
        return itemGoalRelationshipRepository.findByExerciseItemId(itemId)
    }

    override fun deleteByGoalId(goalId: Long) {
        itemGoalRelationshipRepository.deleteGoalId(goalId)
    }

    override fun deleteByItemId(itemId: Long) {
        itemGoalRelationshipRepository.deleteItemId(itemId)
    }
}
