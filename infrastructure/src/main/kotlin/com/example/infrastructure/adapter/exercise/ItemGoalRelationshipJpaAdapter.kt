package com.example.infrastructure.adapter.exercise

import com.example.core.exercise.port.command.AddItemGoalRelationCommand
import com.example.core.exercise.port.out.ItemGoalRelationshipJpaPort
import com.example.infrastructure.persistence.entity.exercise.ItemGoalRelationshipEntity
import com.example.infrastructure.persistence.repository.exercise.ItemGoalRelationshipRepository
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

//    override fun findByItemId(itemId: Long): List<ItemGoalRelationship>? {
//        return itemGoalRelationshipRepository.findByExerciseItemId(itemId)
//    }

    override fun deleteByGoalId(goalId: Long) {
        itemGoalRelationshipRepository.deleteGoalId(goalId)
    }

    override fun deleteByItemId(itemId: Long) {
        itemGoalRelationshipRepository.deleteItemId(itemId)
    }
}
