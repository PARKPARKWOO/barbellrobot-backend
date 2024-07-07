package com.example.core.history.application.port.out.command

import com.example.core.history.model.Diet
import java.util.UUID

data class AddDietImageCommand(
    val todayHistoryId: UUID,
    val imageIds: List<String>,
    val type: Diet,
)
