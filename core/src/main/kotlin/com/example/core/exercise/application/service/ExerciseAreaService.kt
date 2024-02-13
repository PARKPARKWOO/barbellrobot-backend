package com.example.core.exercise.application.service

import com.example.core.exercise.application.`in`.ExerciseAreaUseCase
import com.example.core.exercise.application.`in`.command.SaveExerciseAreaCommand
import com.example.core.exercise.application.out.ExerciseAreaJpaPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ExerciseAreaService(
    private val exerciseAreaJpaPort: ExerciseAreaJpaPort,
//    private val exerciseItemJpaPort: ExerciseItemJpaPort,
) : ExerciseAreaUseCase {
    @Transactional
    override fun saveExerciseArea(command: SaveExerciseAreaCommand) {
        exerciseAreaJpaPort.saveExerciseArea(command)
    }

    @Transactional
    override fun deleteExerciseArea(id: Long) {
        TODO()
    }
}
