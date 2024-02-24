package com.example.core.image.application.service

import com.example.core.image.application.port.`in`.DeleteImageUseCase
import com.example.core.image.application.port.`in`.GenerateImageUseCase
import com.example.core.image.application.port.out.ImageJpaPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ImageService(
    private val imageJpaPort: ImageJpaPort,
) : GenerateImageUseCase, DeleteImageUseCase {
    @Transactional
    override fun deleteImage(vararg ids: Long) {
        imageJpaPort.delete(*ids)
    }

    @Transactional
    override fun generateImage(): Long {
        TODO("Not yet S3")
    }

    @Transactional
    override fun generateImages(): List<Long> {
        TODO("Not yet S3")
    }
}
