package com.example.core.exercise.application.port.command

data class AddItemGoalRelationCommand(
    val itemId: Long,
    val goalIds: List<Long>,
)
