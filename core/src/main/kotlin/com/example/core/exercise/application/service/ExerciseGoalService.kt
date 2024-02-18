package com.example.core.exercise.application.service

import com.example.core.exercise.application.`in`.ExerciseGoalUseCase
import com.example.core.exercise.application.`in`.command.SaveExerciseGoalCommand
import com.example.core.exercise.application.out.ExerciseGoalJpaPort
import com.example.core.exercise.application.out.ItemGoalRelationshipJpaPort
import com.example.domain.exercise.ExerciseGoal
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
