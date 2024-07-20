package com.example.api.exercise.adapter.`in`.request

import com.example.core.exercise.port.command.SaveExerciseGoalCommand

data class CreateExerciseGoalRequest(
    val goal: String,
) {
    fun toCommand(): SaveExerciseGoalCommand {
        return SaveExerciseGoalCommand(
            goal = goal,
        )
    }
}
