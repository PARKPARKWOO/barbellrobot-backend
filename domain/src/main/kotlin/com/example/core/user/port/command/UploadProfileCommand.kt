package com.example.core.user.port.command

import com.example.core.multimedia.dto.MultimediaDto
import java.io.InputStream
import java.util.UUID

data class UploadProfileCommand(
    val id: UUID,
    val contentType: String,
    val contentLength: Long,
    val inputStream: InputStream,
) {
    fun toMultimediaDto(): MultimediaDto = MultimediaDto(
        contentType = contentType,
        contentLength = contentLength,
        inputStream = inputStream,
    )
}
