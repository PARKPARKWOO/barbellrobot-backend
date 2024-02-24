package com.example.core.image.adapter

import com.example.core.image.adapter.out.persistence.entity.ImageEntity
import com.example.core.image.adapter.out.persistence.repository.ImageRepository
import com.example.core.image.application.port.out.ImageJpaPort
import org.springframework.stereotype.Component

@Component
class ImageJpaAdapter(
    private val imageRepository: ImageRepository,
) : ImageJpaPort {
    override fun saveImage(uri: String): Long {
        val imageEntity = ImageEntity(
            uri = uri,
        )
        return imageRepository.save(imageEntity).id
    }

    override fun saveImages(uriList: List<String>): List<Long> {
        return imageRepository.saveAll(
            uriList.map { uri ->
                ImageEntity(
                    uri = uri,
                )
            },
        ).map { entity -> entity.id }
    }

    override fun delete(vararg id: Long) {
        imageRepository.deleteQuery(id.toList())
    }
}
