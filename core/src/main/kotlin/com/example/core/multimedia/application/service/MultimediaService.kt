package com.example.core.multimedia.application.service

import com.example.core.multimedia.application.port.`in`.MultimediaUploadUseCase
import com.example.core.multimedia.application.port.out.MediaJpaPort
import com.example.core.multimedia.application.port.out.S3Port
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class MultimediaService(
    private val s3Port: S3Port,
    private val mediaJpaPort: MediaJpaPort,
) : MultimediaUploadUseCase {
    override suspend fun uploadMultipartFiles(files: List<MultipartFile>): List<Long>? {
        return s3Port.uploadS3(files)?.let {
            mediaJpaPort.saveAllMedia(it)
        }
    }
}
