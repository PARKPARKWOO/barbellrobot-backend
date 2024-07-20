package com.example.core.exercise.port.out

import com.example.core.exercise.port.command.AddItemAreaRelationCommand

interface ItemAreaRelationshipJpaPort {
    fun addRelationship(command: AddItemAreaRelationCommand)

//    fun findByItemId(itemId: Long): List<ItemAreaRelationshipEntity>?

    fun deleteItemId(itemId: Long)

    fun deleteAreaId(areaId: Long)
}
