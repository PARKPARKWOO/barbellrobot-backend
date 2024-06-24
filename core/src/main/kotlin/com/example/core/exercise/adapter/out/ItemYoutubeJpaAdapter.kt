package com.example.core.exercise.adapter.out

import com.example.core.exercise.adapter.out.persistence.repository.ItemYoutubeInfoRepository
import com.example.core.exercise.application.port.command.AddItemYoutubeCommand
import com.example.core.exercise.application.port.out.ItemYoutubeJpaPort
import org.springframework.stereotype.Component

@Component
class ItemYoutubeJpaAdapter(
    private val itemYoutubeInfoRepository: ItemYoutubeInfoRepository,
): ItemYoutubeJpaPort {
    override fun add(command: AddItemYoutubeCommand) {
        Item
    }

    override fun delete(id: Long) {
        TODO("Not yet implemented")
    }
}
