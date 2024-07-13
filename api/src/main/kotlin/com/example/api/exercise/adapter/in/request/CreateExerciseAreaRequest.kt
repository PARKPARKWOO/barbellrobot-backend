package com.example.api.exercise.adapter.`in`.request

import com.example.core.exercise.port.command.SaveExerciseAreaCommand

data class CreateExerciseAreaRequest(
    val area: String,
) {
    fun toCommand(): SaveExerciseAreaCommand {
        return SaveExerciseAreaCommand(
            area = area,
        )
    }
}
