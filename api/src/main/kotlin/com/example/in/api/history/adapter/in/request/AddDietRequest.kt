package com.example.`in`.api.history.adapter.`in`.request

import com.example.core.history.model.Diet
import com.example.core.history.port.command.AddDietCommand
import com.example.`in`.common.MultipartFileConverter.toDto
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
            images = images?.map { it.toDto() },
            todayHistoryId = todayHistoryId,
            type = type,
        )
    }
}
