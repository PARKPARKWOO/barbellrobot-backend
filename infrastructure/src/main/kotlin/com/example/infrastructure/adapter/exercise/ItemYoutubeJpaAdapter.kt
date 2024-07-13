package com.example.infrastructure.adapter.exercise

import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.exercise.adapter.out.persistence.entity.ExerciseItemEntity
import com.example.core.exercise.adapter.out.persistence.entity.relationship.ItemYoutubeInfo
import com.example.core.exercise.adapter.out.persistence.repository.ExerciseItemRepository
import com.example.core.exercise.adapter.out.persistence.repository.ItemYoutubeInfoRepository
import com.example.core.exercise.port.command.AddItemYoutubeCommand
import com.example.core.exercise.port.out.ItemYoutubeJpaPort
import org.springframework.stereotype.Component
import kotlin.jvm.optionals.getOrNull

@Component
class ItemYoutubeJpaAdapter(
    private val itemYoutubeInfoRepository: ItemYoutubeInfoRepository,
    private val exerciseItemRepository: ExerciseItemRepository,
) : ItemYoutubeJpaPort {
    // batch insert 추가
    override fun add(command: AddItemYoutubeCommand) {
        val itemEntity = getItemEntity(command.itemId)
        val itemYoutubeInfo = ItemYoutubeInfo(
            item = itemEntity,
            videoType = command.videoType,
            title = command.title,
            channel = command.channel,
            youtubeUrl = command.youtubeUrl,
        )
        itemYoutubeInfoRepository.save(itemYoutubeInfo)
    }

    override fun delete(id: Long) {
        itemYoutubeInfoRepository.findById(id).getOrNull()?.let { entity ->
            itemYoutubeInfoRepository.delete(entity)
        }
    }

    private fun getItemEntity(id: Long): ExerciseItemEntity {
        return exerciseItemRepository.findById(id).orElseThrow {
            throw ServiceException(ErrorCode.NOT_FOUND_EXERCISE_ITEM)
        }
    }
}
