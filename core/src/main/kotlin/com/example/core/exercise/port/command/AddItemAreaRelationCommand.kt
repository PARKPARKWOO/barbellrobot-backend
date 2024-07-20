package com.example.core.exercise.port.command

data class AddItemAreaRelationCommand(
    val itemId: Long,
    val areaIds: List<Long>,
)
