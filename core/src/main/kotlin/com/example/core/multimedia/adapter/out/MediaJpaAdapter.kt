package com.example.core.multimedia.adapter.out

import com.example.core.multimedia.adapter.out.persistence.entity.MultimediaEntity
import com.example.core.multimedia.adapter.out.persistence.repository.MultimediaRepository
import com.example.core.multimedia.application.port.out.MediaJpaPort
import org.springframework.stereotype.Component

@Component
class MediaJpaAdapter(
    private val multimediaRepository: MultimediaRepository,
) : MediaJpaPort {
    override fun saveAllMedia(uri: List<String>): List<Long> {
        return multimediaRepository.saveAll(
            uri.map {
                MultimediaEntity(
                    uri = it,
                )
            },
        ).map {
            it.id
        }
    }
}
