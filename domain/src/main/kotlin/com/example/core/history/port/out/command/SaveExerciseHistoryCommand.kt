package com.example.core.history.port.out.command

import java.util.UUID

data class SaveExerciseHistoryCommand(
    var itemId: Long,
    var weight: Double,
    var exerciseSet: Int,
    var userHistoryId: UUID,
    val count: Int?,
)
