package com.example.application.exercise.service

import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.exercise.model.ExerciseGoal
import com.example.core.exercise.port.command.SaveExerciseGoalCommand
import com.example.core.exercise.port.`in`.ExerciseGoalUseCase
import com.example.core.exercise.port.out.ExerciseGoalJpaPort
import com.example.core.exercise.port.out.ItemGoalRelationshipJpaPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ExerciseGoalService(
    private val exerciseGoalJpaPort: ExerciseGoalJpaPort,
    private val itemGoalRelationshipJpaPort: ItemGoalRelationshipJpaPort,
) : ExerciseGoalUseCase {
    @Transactional
    override fun saveExerciseGoal(command: SaveExerciseGoalCommand) {
        exerciseGoalJpaPort.saveExerciseGoal(command)
    }

    @Transactional
    override fun deleteExerciseGoal(id: Long) {
        exerciseGoalJpaPort.getExerciseGoal(id)?.let {
            exerciseGoalJpaPort.delete(id)
            itemGoalRelationshipJpaPort.deleteByGoalId(id)
        }
    }

    @Transactional(readOnly = true)
    override fun getGoal(id: Long): ExerciseGoal {
        return exerciseGoalJpaPort.getExerciseGoal(id) ?: throw ServiceException(ErrorCode.NOT_FOUND_EXERCISE_GOAL)
    }

    @Transactional(readOnly = true)
    override fun getAllGoals(): List<ExerciseGoal> {
        return exerciseGoalJpaPort.getAll() ?: emptyList()
    }
}
