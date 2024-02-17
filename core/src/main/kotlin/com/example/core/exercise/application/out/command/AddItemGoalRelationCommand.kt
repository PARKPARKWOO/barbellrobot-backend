package com.example.core.exercise.application.out.command

data class AddItemGoalRelationCommand(
    val itemId: Long,
    val goalIds: List<Long>,
)
