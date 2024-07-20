package com.example.infrastructure.adapter.exercise

import com.example.core.exercise.port.command.AddItemAreaRelationCommand
import com.example.core.exercise.port.out.ItemAreaRelationshipJpaPort
import com.example.infrastructure.persistence.entity.exercise.ItemAreaRelationshipEntity
import com.example.infrastructure.persistence.repository.exercise.ItemAreaRelationshipRepository
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

//    override fun findByItemId(itemId: Long): List<ItemAreaRelationshipEntity>? {
//        return itemAreaRelationshipRepository.findByExerciseItemId(itemId)
//    }

    override fun deleteItemId(itemId: Long) {
        itemAreaRelationshipRepository.deleteItemId(itemId)
    }

    override fun deleteAreaId(areaId: Long) {
        itemAreaRelationshipRepository.deleteAreaId(areaId)
    }
}
