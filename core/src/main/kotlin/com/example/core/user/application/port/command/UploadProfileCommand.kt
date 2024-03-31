package com.example.core.user.application.port.command

import org.springframework.web.multipart.MultipartFile
import java.util.UUID

data class UploadProfileCommand(
    val id: UUID,
    val file: MultipartFile,
)
