package com.example.core.exercise.application.port.command

data class AddItemAreaRelationCommand(
    val itemId: Long,
    val areaIds: List<Long>,
)
