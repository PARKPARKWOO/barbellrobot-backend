package com.example.application.mutlimedia

import com.example.core.multimedia.dto.MultimediaDto
import com.example.core.multimedia.port.`in`.MultimediaUploadUseCase
import com.example.core.multimedia.port.out.S3Port
import org.springframework.stereotype.Service

@Service
class MultimediaService(
    private val s3Port: S3Port,
) : MultimediaUploadUseCase {
    override suspend fun uploadMultipartFiles(files: List<MultimediaDto>): List<String>? {
        return s3Port.uploadFiles(files)
    }

    override suspend fun uploadMultipartFile(file: MultimediaDto): String? {
        val command = listOf(file)
        return s3Port.uploadFiles(command)?.get(0)
    }
}
