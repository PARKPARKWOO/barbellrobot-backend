package com.example.core.history.application.port.`in`.command

import com.example.domain.history.Diet
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

data class AddDietCommand(
    val userId: UUID,
    val type: Diet,
    val todayHistoryId: UUID,
    val images: List<MultipartFile>?,
    val foods: List<String>?,
)
