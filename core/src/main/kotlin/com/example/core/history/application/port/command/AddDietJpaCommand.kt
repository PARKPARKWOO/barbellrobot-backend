package com.example.core.history.application.port.command

import com.example.core.history.application.port.out.command.AddDietFoodCommand
import com.example.core.history.application.port.out.command.AddDietImageCommand
import com.example.domain.history.Diet
import java.util.UUID

data class AddDietJpaCommand(
    val type: Diet,
    val todayHistoryId: UUID,
    val images: List<String>?,
    val foods: List<String>?,
) {
    fun toFoodCommand() = AddDietFoodCommand(
        todayHistoryId = todayHistoryId,
        type = type,
        foods = foods ?: emptyList(),
    )

    fun toImageCommand() = AddDietImageCommand(
        todayHistoryId = todayHistoryId,
        type = type,
        imageIds = images ?: emptyList(),
    )
}
