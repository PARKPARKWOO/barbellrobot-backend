package com.example.core.history.application.port.out.command

import com.example.domain.history.Diet
import java.util.UUID

data class AddDietFoodCommand(
    val todayHistoryId: UUID,
    val foods: List<String>,
    val type: Diet,
)
