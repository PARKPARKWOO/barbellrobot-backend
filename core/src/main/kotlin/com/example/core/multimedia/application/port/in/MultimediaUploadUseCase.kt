package com.example.core.multimedia.application.port.`in`

import org.springframework.web.multipart.MultipartFile

interface MultimediaUploadUseCase {
    suspend fun uploadMultipartFiles(files: List<MultipartFile>): List<Long>?
}
