package com.example.core.exercise.application.service

import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.exercise.application.`in`.ExerciseItemUseCase
import com.example.core.exercise.application.`in`.command.SaveExerciseItemCommand
import com.example.core.exercise.application.out.ExerciseAreaJpaPort
import com.example.core.exercise.application.out.ExerciseGoalJpaPort
import com.example.core.exercise.application.out.ExerciseItemJpaPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ExerciseItemService(
    private val exerciseItemJpaPort: ExerciseItemJpaPort,
    private val exerciseGoalJpaPort: ExerciseGoalJpaPort,
    private val exerciseAreaJpaPort: ExerciseAreaJpaPort,
) : ExerciseItemUseCase {
    @Transactional
    override fun saveExerciseItem(command: SaveExerciseItemCommand) {
        val exerciseGoals = exerciseGoalJpaPort.getExerciseGoals(command.exerciseGoals)
        val exerciseAreas = exerciseAreaJpaPort.getExerciseAreas(command.exerciseAreas)
        if (exerciseAreas != command.exerciseAreas) {
            throw ServiceException(ErrorCode.NOT_FOUND_EXERCISE_AREA)
        }
        if (exerciseGoals != command.exerciseGoals) {
            throw ServiceException(ErrorCode.NOT_FOUND_EXERCISE_GOAL)
        }
        exerciseItemJpaPort.saveExerciseItem(command)
    }
}
