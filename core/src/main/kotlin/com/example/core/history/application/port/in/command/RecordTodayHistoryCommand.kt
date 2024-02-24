package com.example.core.history.application.port.`in`.command

import org.springframework.web.multipart.MultipartFile
import java.util.UUID

data class RecordTodayHistoryCommand(
    val userId: UUID,
    val imageFiles: List<MultipartFile>?,
    val videoFiles: List<MultipartFile>?,
)
