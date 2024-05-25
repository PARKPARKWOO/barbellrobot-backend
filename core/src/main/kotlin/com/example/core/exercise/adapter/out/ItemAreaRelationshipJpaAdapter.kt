package com.example.core.exercise.adapter.out

import com.example.core.exercise.adapter.out.persistence.entity.relationship.ItemAreaRelationshipEntity
import com.example.core.exercise.adapter.out.persistence.repository.ItemAreaRelationshipRepository
import com.example.core.exercise.application.port.out.ItemAreaRelationshipJpaPort
import com.example.core.exercise.application.port.command.AddItemAreaRelationCommand
import org.springframework.stereotype.Component

@Component
class ItemAreaRelationshipJpaAdapter(
    private val itemAreaRelationshipRepository: ItemAreaRelationshipRepository,
) : ItemAreaRelationshipJpaPort {
    override fun addRelationship(command: AddItemAreaRelationCommand) {
        val list = mutableListOf<ItemAreaRelationshipEntity>()
        for (areaId in command.areaIds) {
            val itemAreaRelationshipEntity = ItemAreaRelationshipEntity(
                exerciseItemId = command.itemId,
                exerciseAreaId = areaId,
            )
            list.add(itemAreaRelationshipEntity)
        }
        itemAreaRelationshipRepository.saveAll(list)
    }

    override fun findByItemId(itemId: Long): List<ItemAreaRelationshipEntity>? {
        return itemAreaRelationshipRepository.findByExerciseItemId(itemId)
    }

    override fun deleteItemId(itemId: Long) {
        itemAreaRelationshipRepository.deleteItemId(itemId)
    }

    override fun deleteAreaId(areaId: Long) {
        itemAreaRelationshipRepository.deleteAreaId(areaId)
    }
}
