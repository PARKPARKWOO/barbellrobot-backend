package com.example.infrastructure.adapter.history

import com.example.core.history.adapter.out.persistence.entity.ExerciseHistoryEntity
import com.example.core.history.adapter.out.persistence.repository.ExerciseHistoryRepository
import com.example.core.history.port.out.ExerciseHistoryJpaPort
import com.example.core.history.port.out.command.SaveExerciseHistoryCommand
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class ExerciseHistoryJpaAdapter(
    private val exerciseHistoryRepository: ExerciseHistoryRepository,
) : ExerciseHistoryJpaPort {
    override fun save(command: SaveExerciseHistoryCommand) {
        val exerciseHistoryEntity = ExerciseHistoryEntity(
            itemId = command.itemId,
            weight = command.weight,
            exerciseSet = command.exerciseSet,
            userHistoryId = command.userHistoryId,
            createdAt = LocalDate.now(),
            count = command.count,
        )
        exerciseHistoryRepository.save(exerciseHistoryEntity)
    }

    override fun saveAll(command: List<SaveExerciseHistoryCommand>) {
        val transform: (SaveExerciseHistoryCommand) -> ExerciseHistoryEntity = { dto ->
            ExerciseHistoryEntity(
                itemId = dto.itemId,
                weight = dto.weight,
                exerciseSet = dto.exerciseSet,
                userHistoryId = dto.userHistoryId,
                createdAt = LocalDate.now(),
                count = dto.count,
            )
        }
        val entities = command.map(transform)
        exerciseHistoryRepository.saveAll(entities)
    }
}
