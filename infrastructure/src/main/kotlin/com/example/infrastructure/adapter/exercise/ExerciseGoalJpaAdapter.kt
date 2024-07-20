package com.example.infrastructure.adapter.exercise

import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.exercise.model.ExerciseGoal
import com.example.core.exercise.port.command.SaveExerciseGoalCommand
import com.example.core.exercise.port.out.ExerciseGoalJpaPort
import com.example.infrastructure.persistence.entity.exercise.ExerciseGoalEntity
import com.example.infrastructure.persistence.repository.exercise.ExerciseGoalRepository
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

    override fun getExerciseGoal(id: Long): ExerciseGoal? {
        return getEntity(id).toDomain()
    }

    override fun getExerciseGoals(ids: List<Long>): List<ExerciseGoal> {
        return exerciseGoalRepository.queryIdsIn(ids)?.map { it.toDomain() } ?: emptyList()
    }

    override fun delete(id: Long) {
        exerciseGoalRepository.deleteById(id)
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
