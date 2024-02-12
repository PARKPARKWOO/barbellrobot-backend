package com.example.core.exercise.adapter.out

import com.example.core.exercise.adapter.out.persistence.entity.ExerciseItemEntity
import com.example.core.exercise.adapter.out.persistence.repository.ExerciseItemRepository
import com.example.core.exercise.application.`in`.command.SaveExerciseItemCommand
import com.example.core.exercise.application.out.ExerciseItemJpaPort
import org.springframework.stereotype.Component

@Component
class ExerciseItemJpaAdapter(
    private val exerciseItemRepository: ExerciseItemRepository,
) : ExerciseItemJpaPort {
    override fun saveExerciseItem(command: SaveExerciseItemCommand) {
        val entity = ExerciseItemEntity(
            exerciseAreas = command.exerciseAreas,
            exerciseName = command.exerciseName,
            videoUri = command.videoUri,
            imageUri = command.imageUri,
            exerciseGoals = command.exerciseGoals,
        )
        exerciseItemRepository.save(entity)
    }
}
