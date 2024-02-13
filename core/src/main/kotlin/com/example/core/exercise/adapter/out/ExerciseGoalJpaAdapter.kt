package com.example.core.exercise.adapter.out

import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.exercise.adapter.out.persistence.entity.ExerciseGoalEntity
import com.example.core.exercise.adapter.out.persistence.repository.ExerciseGoalRepository
import com.example.core.exercise.application.`in`.command.SaveExerciseGoalCommand
import com.example.core.exercise.application.out.ExerciseGoalJpaPort
import com.example.domain.exercise.ExerciseGoal
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

    override fun getExerciseGoal(id: Long): ExerciseGoal {
        return getEntity(id).toDomain()
    }

    override fun getExerciseGoals(ids: List<Long>): List<ExerciseGoal>? {
        return exerciseGoalRepository.findByIds(ids)?.map { entity ->
            entity.toDomain()
        }
    }

    override fun delete(id: Long) {
        val entity = getEntity(id)
        exerciseGoalRepository.delete(entity)
    }

    private fun getEntity(id: Long): ExerciseGoalEntity {
        return exerciseGoalRepository.findById(id).orElseThrow {
            throw ServiceException(ErrorCode.NOT_FOUND_EXERCISE_GOAL)
        }
    }
}
