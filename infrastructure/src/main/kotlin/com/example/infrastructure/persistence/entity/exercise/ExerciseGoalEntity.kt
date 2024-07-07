package com.example.infrastructure.persistence.entity.exercise

import com.example.core.exercise.model.ExerciseGoal
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id
import jakarta.persistence.Table

const val EXERCISE_GOAL_TABLE_NAME = "exercise_goal"
const val EXERCISE_GOAL_ID = EXERCISE_GOAL_TABLE_NAME + "_id"

@Entity
@Table(name = EXERCISE_GOAL_TABLE_NAME)
class ExerciseGoalEntity(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    var id: Long = 0L,
    @Column(name = "goal")
    var goal: String,
) {
    fun toDomain(): ExerciseGoal {
        return ExerciseGoal(
            id = this.id,
            goal = this.goal,
        )
    }
}
