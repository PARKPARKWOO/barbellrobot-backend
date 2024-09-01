package com.example.core.exercise.port.command

data class AddItemGoalRelationCommand(
    val itemId: Long,
    val goalIds: List<Long>,
)
