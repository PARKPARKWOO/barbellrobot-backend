package com.example.core.multimedia.port.`in`

import com.example.core.multimedia.dto.MultimediaDto

interface MultimediaUploadUseCase {
    suspend fun uploadMultipartFiles(files: List<MultimediaDto>): List<String>?

    suspend fun uploadMultipartFile(file: MultimediaDto): String?
}
