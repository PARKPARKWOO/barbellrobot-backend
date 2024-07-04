package com.example.api.history.adapter.`in`.request

import com.example.core.history.application.port.command.AddDietCommand
import com.example.domain.history.Diet
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

data class AddDietRequest(
    val type: Diet,
    val todayHistoryId: UUID,
    val foods: List<String>?,
) {
    fun toCommand(userId: UUID, images: List<MultipartFile>?): AddDietCommand {
        return AddDietCommand(
            userId = userId,
            foods = foods,
            images = images,
            todayHistoryId = todayHistoryId,
            type = type,
        )
    }
}
