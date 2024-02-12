package com.example.core.exercise.application.service

import com.example.core.exercise.application.`in`.ExerciseGoalUseCase
import com.example.core.exercise.application.`in`.command.SaveExerciseGoalCommand
import com.example.core.exercise.application.out.ExerciseGoalJpaPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ExerciseGoalService(
    private val exerciseGoalJpaPort: ExerciseGoalJpaPort,
) : ExerciseGoalUseCase {
    @Transactional
    override fun saveExerciseGoal(command: SaveExerciseGoalCommand) {
        exerciseGoalJpaPort.saveExerciseGoal(command)
    }
}
