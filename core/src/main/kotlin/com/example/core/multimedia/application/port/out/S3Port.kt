package com.example.core.multimedia.application.port.out

import org.springframework.web.multipart.MultipartFile

interface S3Port {
    suspend fun uploadS3(files: List<MultipartFile>): List<String>?
}
