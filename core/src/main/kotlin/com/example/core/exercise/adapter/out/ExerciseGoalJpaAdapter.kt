package com.example.core.exercise.adapter.out

import com.example.core.exercise.adapter.out.persistence.entity.ExerciseGoalEntity
import com.example.core.exercise.adapter.out.persistence.repository.ExerciseGoalRepository
import com.example.core.exercise.application.`in`.command.SaveExerciseGoalCommand
import com.example.core.exercise.application.out.ExerciseGoalJpaPort
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
}
