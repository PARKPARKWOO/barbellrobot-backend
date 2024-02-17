package com.example.core.exercise.application.out.command

data class AddItemAreaRelationCommand(
    val itemId: Long,
    val areaIds: List<Long>,
)
