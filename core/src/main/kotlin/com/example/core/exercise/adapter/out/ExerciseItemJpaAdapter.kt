package com.example.core.exercise.adapter.out

import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.exercise.adapter.out.persistence.entity.ExerciseItemEntity
import com.example.core.exercise.adapter.out.persistence.repository.ExerciseItemRepository
import com.example.core.exercise.application.dto.QueryItemDto
import com.example.core.exercise.application.out.ExerciseItemJpaPort
import com.example.core.exercise.application.out.command.SaveExerciseItemOutCommand
import com.example.domain.exercise.ExerciseItem
import org.springframework.stereotype.Component

@Component
class ExerciseItemJpaAdapter(
    private val exerciseItemRepository: ExerciseItemRepository,
) : ExerciseItemJpaPort {
    override fun saveExerciseItem(command: SaveExerciseItemOutCommand): Long {
        val entity = ExerciseItemEntity(
            exerciseName = command.exerciseName,
            videoUri = command.videoUri,
            imageUri = command.imageUri,
        )
        return exerciseItemRepository.save(entity).id
    }

    override fun queryItem(id: Long): QueryItemDto? {
        return exerciseItemRepository.findItemAndAreaAndGoal(id)
    }

    override fun findById(id: Long): ExerciseItem {
        return getEntity(id).toDomain()
    }

    private fun getEntity(id: Long): ExerciseItemEntity {
        return exerciseItemRepository.findById(id).orElseThrow {
            throw ServiceException(ErrorCode.NOT_FOUND_EXERCISE_ITEM)
        }
    }
}
