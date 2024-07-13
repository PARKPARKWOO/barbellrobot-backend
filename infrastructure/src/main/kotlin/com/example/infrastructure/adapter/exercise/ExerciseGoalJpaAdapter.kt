package com.example.infrastructure.adapter.exercise

import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.exercise.adapter.out.persistence.entity.ExerciseGoalEntity
import com.example.core.exercise.adapter.out.persistence.repository.ExerciseGoalRepository
import com.example.core.exercise.port.command.SaveExerciseGoalCommand
import com.example.core.exercise.port.out.ExerciseGoalJpaPort
import com.example.core.exercise.model.ExerciseGoal
import org.springframework.stereotype.Component

@Component
class ExerciseGoalJpaAdapter(
    private val exerciseGoalRepository: ExerciseGoalRepository,
) : ExerciseGoalJpaPort {
    override fun saveExerciseGoal(command: SaveExerciseGoalCommand) {
        val entity = ExerciseGoalEntity(
            goal = command.goal,
        )
        exerciseGoalRepository.save(entity)
    }

    override fun getExerciseGoal(id: Long): ExerciseGoalEntity {
        return getEntity(id)
    }

    override fun getExerciseGoals(ids: List<Long>): List<ExerciseGoalEntity>? {
        return exerciseGoalRepository.queryIdsIn(ids)
    }

    override fun delete(entity: ExerciseGoalEntity) {
        exerciseGoalRepository.delete(entity)
    }

    override fun getAll(): List<ExerciseGoal>? {
        return exerciseGoalRepository.findAll().map { goal ->
            goal.toDomain()
        }
    }

    private fun getEntity(id: Long): ExerciseGoalEntity {
        return exerciseGoalRepository.findById(id).orElseThrow {
            throw ServiceException(ErrorCode.NOT_FOUND_EXERCISE_GOAL)
        }
    }
}
