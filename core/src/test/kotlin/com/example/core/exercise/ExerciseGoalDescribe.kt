package com.example.core.exercise

import com.example.core.exercise.application.out.ExerciseGoalJpaPort
import com.example.core.exercise.application.out.ExerciseItemJpaPort
import com.example.core.exercise.application.service.ExerciseGoalService
import com.example.core.exercise.util.ExerciseGoalTestUtil
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk

class ExerciseGoalDescribe : DescribeSpec(
    {
        val exerciseGoalJpaPort: ExerciseGoalJpaPort = mockk()
        val exerciseItemJpaPort: ExerciseItemJpaPort = mockk()
        val exerciseGoalService = ExerciseGoalService(exerciseGoalJpaPort, exerciseItemJpaPort)
        describe("ExerciseGoal 을 생성할때") {
            context("정상적인 요청이라면") {
                it("Success") {
                    val saveCommand = ExerciseGoalTestUtil.saveExerciseGoalCommand
                    every { exerciseGoalJpaPort.saveExerciseGoal(any()) } returns Unit
                    exerciseGoalService.saveExerciseGoal(saveCommand)
                }
            }
        }
    },
)
