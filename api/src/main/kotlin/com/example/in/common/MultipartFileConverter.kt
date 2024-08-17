package com.example.`in`.common

import com.example.core.multimedia.dto.MultimediaDto
import org.springframework.web.multipart.MultipartFile

object MultipartFileConverter {
    fun MultipartFile.toDto(): MultimediaDto = MultimediaDto(
        contentLength = this.size,
        contentType = this.contentType!!,
        inputStream = this.inputStream,
    )
}
