package com.example.core.exercise.adapter.out

import com.example.core.exercise.adapter.out.persistence.entity.ExerciseAreaEntity
import com.example.core.exercise.adapter.out.persistence.repository.ExerciseAreaRepository
import com.example.core.exercise.application.`in`.command.SaveExerciseAreaCommand
import com.example.core.exercise.application.out.ExerciseAreaJpaPort
import org.springframework.stereotype.Component

@Component
class ExerciseAreaJpaAdapter(
    private val exerciseAreaRepository: ExerciseAreaRepository,
): ExerciseAreaJpaPort {
    override fun saveExerciseArea(command: SaveExerciseAreaCommand) {
        val entity = ExerciseAreaEntity(
            area = command.area,
        )
        exerciseAreaRepository.save(entity)
    }
}
