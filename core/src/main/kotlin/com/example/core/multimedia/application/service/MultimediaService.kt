package com.example.core.multimedia.application.service

import com.example.core.multimedia.application.port.`in`.MultimediaUploadUseCase
import com.example.core.multimedia.application.port.out.S3Port
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class MultimediaService(
    private val s3Port: S3Port,
) : MultimediaUploadUseCase {
    override suspend fun uploadMultipartFiles(files: List<MultipartFile>): List<String>? {
        return s3Port.uploadFiles(files)
    }

    override suspend fun uploadMultipartFile(file: MultipartFile): String? {
        val command = listOf(file)
        return s3Port.uploadFiles(command)?.get(0)
    }
}
