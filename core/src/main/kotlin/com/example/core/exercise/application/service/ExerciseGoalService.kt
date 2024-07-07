package com.example.core.exercise.application.service

import com.example.core.exercise.application.port.`in`.ExerciseGoalUseCase
import com.example.core.exercise.application.port.command.SaveExerciseGoalCommand
import com.example.core.exercise.application.port.out.ExerciseGoalJpaPort
import com.example.core.exercise.application.port.out.ItemGoalRelationshipJpaPort
import com.example.core.exercise.model.ExerciseGoal
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
        val goalEntity = exerciseGoalJpaPort.getExerciseGoal(id)
        exerciseGoalJpaPort.delete(goalEntity)
        itemGoalRelationshipJpaPort.deleteByGoalId(id)
    }

    @Transactional(readOnly = true)
    override fun getGoal(id: Long): ExerciseGoal {
        return exerciseGoalJpaPort.getExerciseGoal(id).toDomain()
    }

    @Transactional(readOnly = true)
    override fun getAllGoals(): List<ExerciseGoal> {
        return exerciseGoalJpaPort.getAll() ?: emptyList()
    }
}
