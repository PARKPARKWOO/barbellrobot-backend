package com.example.core.history.port.command

import com.example.core.history.model.Diet
import com.example.core.multimedia.dto.MultimediaDto
import java.util.UUID

data class AddDietCommand(
    val userId: UUID,
    val type: Diet,
    val todayHistoryId: UUID,
    val images: List<MultimediaDto>?,
    val foods: List<String>?,
)
