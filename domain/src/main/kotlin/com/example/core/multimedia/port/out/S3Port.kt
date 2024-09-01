package com.example.core.multimedia.port.out

import com.example.core.multimedia.dto.MultimediaDto

interface S3Port {
    suspend fun uploadFiles(files: List<MultimediaDto>): List<String>?
}
