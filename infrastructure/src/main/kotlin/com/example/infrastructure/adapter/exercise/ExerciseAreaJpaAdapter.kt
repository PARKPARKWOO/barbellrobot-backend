package com.example.infrastructure.adapter.exercise

import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.exercise.model.ExerciseArea
import com.example.core.exercise.port.command.SaveExerciseAreaCommand
import com.example.core.exercise.port.out.ExerciseAreaJpaPort
import com.example.infrastructure.persistence.entity.exercise.ExerciseAreaEntity
import com.example.infrastructure.persistence.repository.exercise.ExerciseAreaRepository
import org.springframework.stereotype.Component

@Component
class ExerciseAreaJpaAdapter(
    private val exerciseAreaRepository: ExerciseAreaRepository,
) : ExerciseAreaJpaPort {
    override fun saveExerciseArea(command: SaveExerciseAreaCommand) {
        val entity = ExerciseAreaEntity(
            area = command.area,
        )
        exerciseAreaRepository.save(entity)
    }

    override fun getExerciseArea(id: Long): ExerciseArea {
        return getEntity(id).toDomain()
    }

    override fun getExerciseAreas(ids: List<Long>): List<ExerciseArea> {
        return exerciseAreaRepository.queryIdsIn(ids)?.map { it.toDomain() } ?: emptyList()
    }

    override fun delete(id: Long) {
        val entity = getEntity(id)
        exerciseAreaRepository.delete(entity)
    }

    override fun getAll(): List<ExerciseArea> {
        return exerciseAreaRepository.findAll().map { area ->
            area.toDomain()
        }
    }

    private fun getEntity(id: Long): ExerciseAreaEntity {
        return exerciseAreaRepository.findById(id).orElseThrow {
            throw ServiceException(ErrorCode.NOT_FOUND_EXERCISE_AREA)
        }
    }
}
