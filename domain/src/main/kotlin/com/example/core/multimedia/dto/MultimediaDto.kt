package com.example.core.multimedia.dto

import java.io.InputStream

data class MultimediaDto(
    val contentType: String,
    val contentLength: Long,
    val inputStream: InputStream,
)
