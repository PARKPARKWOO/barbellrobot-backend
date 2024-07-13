package com.example.application.exercise.service

import com.example.core.exercise.model.ExerciseArea
import com.example.core.exercise.port.command.SaveExerciseAreaCommand
import com.example.core.exercise.port.`in`.ExerciseAreaUseCase
import com.example.core.exercise.port.out.ExerciseAreaJpaPort
import com.example.core.exercise.port.out.ItemAreaRelationshipJpaPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ExerciseAreaService(
    private val exerciseAreaJpaPort: ExerciseAreaJpaPort,
    private val itemAreaRelationshipJpaPort: ItemAreaRelationshipJpaPort,
) : ExerciseAreaUseCase {
    @Transactional
    override fun saveExerciseArea(command: SaveExerciseAreaCommand) {
        exerciseAreaJpaPort.saveExerciseArea(command)
    }

    @Transactional
    override fun deleteExerciseArea(id: Long) {
        exerciseAreaJpaPort.delete(id)
        itemAreaRelationshipJpaPort.deleteAreaId(id)
    }

    @Transactional(readOnly = true)
    override fun getExerciseArea(id: Long): ExerciseArea {
        return exerciseAreaJpaPort.getExerciseArea(id)
    }

    @Transactional(readOnly = true)
    override fun getAllExerciseArea(): List<ExerciseArea> {
        return exerciseAreaJpaPort.getAll() ?: emptyList()
    }
}
