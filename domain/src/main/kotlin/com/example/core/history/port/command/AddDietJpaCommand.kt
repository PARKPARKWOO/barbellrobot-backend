package com.example.core.history.port.command

import com.example.core.history.port.out.command.AddDietFoodCommand
import com.example.core.history.port.out.command.AddDietImageCommand
import com.example.core.history.model.Diet
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
