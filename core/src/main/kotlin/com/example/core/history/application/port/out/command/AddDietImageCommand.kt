package com.example.core.history.application.port.out.command

import com.example.domain.history.Diet
import java.util.UUID

data class AddDietImageCommand(
    val todayHistoryId: UUID,
    val imageIds: List<Long>,
    val type: Diet,
)
