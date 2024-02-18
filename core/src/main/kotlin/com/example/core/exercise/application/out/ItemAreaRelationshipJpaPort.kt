package com.example.core.exercise.application.out

import com.example.core.exercise.adapter.out.persistence.entity.relationship.ItemAreaRelationshipEntity
import com.example.core.exercise.application.out.command.AddItemAreaRelationCommand

interface ItemAreaRelationshipJpaPort {
    fun addRelationship(command: AddItemAreaRelationCommand)

    fun findByItemId(itemId: Long): List<ItemAreaRelationshipEntity>?

    fun deleteItemId(itemId: Long)

    fun deleteAreaId(areaId: Long)
}
