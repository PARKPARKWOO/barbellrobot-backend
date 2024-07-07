package com.example.infrastructure.adapter.exercise

import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.exercise.adapter.out.persistence.entity.ExerciseItemEntity
import com.example.core.exercise.adapter.out.persistence.repository.ExerciseItemRepository
import com.example.core.exercise.application.dto.QueryItemDto
import com.example.core.exercise.application.port.command.SaveExerciseItemOutCommand
import com.example.core.exercise.application.port.out.ExerciseItemJpaPort
import com.example.core.exercise.model.ExerciseItem
import org.springframework.stereotype.Component

@Component
class ExerciseItemJpaAdapter(
    private val exerciseItemRepository: ExerciseItemRepository,
) : ExerciseItemJpaPort {
    override fun saveExerciseItem(command: SaveExerciseItemOutCommand): Long {
        val entity = ExerciseItemEntity(
            exerciseName = command.exerciseName,
            videoUri = command.videoUri.toMutableList(),
            imageUri = command.imageUri.toMutableList(),
        )
        return exerciseItemRepository.save(entity).id
    }

    override fun queryItem(id: Long): QueryItemDto? {
        return exerciseItemRepository.findItemAndAreaAndGoal(id)
    }

    override fun findById(id: Long): ExerciseItem {
        return getEntity(id).toDomain()
    }

    override fun delete(id: Long) {
        val entity = getEntity(id)
        exerciseItemRepository.delete(entity)
    }

    override fun findAll(): List<ExerciseItem> {
        return exerciseItemRepository.findAll().map { it.toDomain() }
    }

    override fun findAllItemsQuery(): List<QueryItemDto> {
        return exerciseItemRepository.findItemDetailAll()
    }

    override fun findInIds(ids: List<Long>): List<QueryItemDto> {
        return exerciseItemRepository.findInIds(ids)
    }

    private fun getEntity(id: Long): ExerciseItemEntity {
        return exerciseItemRepository.findById(id).orElseThrow {
            throw ServiceException(ErrorCode.NOT_FOUND_EXERCISE_ITEM)
        }
    }
}
