package com.example.core.history.port.out.command

import com.example.core.history.model.Diet
import java.util.UUID

data class AddDietFoodCommand(
    val todayHistoryId: UUID,
    val foods: List<String>,
    val type: Diet,
)
