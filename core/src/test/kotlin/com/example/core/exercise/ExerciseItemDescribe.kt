package com.example.core.exercise

import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.exercise.application.port.out.ExerciseAreaJpaPort
import com.example.core.exercise.application.port.out.ExerciseGoalJpaPort
import com.example.core.exercise.application.port.out.ExerciseItemJpaPort
import com.example.core.exercise.application.port.out.ItemAreaRelationshipJpaPort
import com.example.core.exercise.application.port.out.ItemGoalRelationshipJpaPort
import com.example.core.exercise.application.service.ExerciseItemService
import com.example.core.exercise.util.ExerciseAreaTestUtil
import com.example.core.exercise.util.ExerciseGoalTestUtil
import com.example.core.exercise.util.ExerciseItemTestUtil
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class ExerciseItemDescribe : DescribeSpec(
    {
        val itemAreaRelationshipJpaPort: ItemAreaRelationshipJpaPort = mockk()
        val itemGoalRelationshipJpaPort: ItemGoalRelationshipJpaPort = mockk()
        val exerciseAreaJpaPort: ExerciseAreaJpaPort = mockk()
        val exerciseGoalJpaPort: ExerciseGoalJpaPort = mockk()
        val exerciseItemJpaPort: ExerciseItemJpaPort = mockk()
        val exerciseItemService = ExerciseItemService(
            exerciseAreaJpaPort = exerciseAreaJpaPort,
            exerciseGoalJpaPort = exerciseGoalJpaPort,
            exerciseItemJpaPort = exerciseItemJpaPort,
            itemAreaRelationshipJpaPort = itemAreaRelationshipJpaPort,
            itemGoalRelationshipJpaPort = itemGoalRelationshipJpaPort,
        )

        describe("ExerciseItem save") {
            val saveCommand = ExerciseItemTestUtil.saveCommand
            context("if area is null") {
                it("throw ServiceException") {
                    every { exerciseAreaJpaPort.getExerciseAreas(any()) } returns null
                    every { exerciseGoalJpaPort.getExerciseGoals(any()) } returns ExerciseGoalTestUtil.entityList
                    val exception = shouldThrow<ServiceException> {
                        exerciseItemService.saveExerciseItem(saveCommand)
                    }
                    exception.errorCode shouldBe ErrorCode.NOT_FOUND_EXERCISE_AREA
                }
            }

            context("if goal is null") {
                it("throw ServiceException") {
                    every { exerciseAreaJpaPort.getExerciseAreas(any()) } returns ExerciseAreaTestUtil.entityList
                    every { exerciseGoalJpaPort.getExerciseGoals(any()) } returns null
                    val exception = shouldThrow<ServiceException> {
                        exerciseItemService.saveExerciseItem(saveCommand)
                    }
                    exception.errorCode shouldBe ErrorCode.NOT_FOUND_EXERCISE_GOAL
                }
            }

            context("if good request") {
                it("save success") {
                    every { exerciseAreaJpaPort.getExerciseAreas(any()) } returns ExerciseAreaTestUtil.entityList
                    every { exerciseGoalJpaPort.getExerciseGoals(any()) } returns ExerciseGoalTestUtil.entityList
                    every { itemAreaRelationshipJpaPort.addRelationship(any()) } returns Unit
                    every { itemGoalRelationshipJpaPort.addRelationship(any()) } returns Unit
                    every { exerciseItemJpaPort.saveExerciseItem(any()) } returns ExerciseItemTestUtil.ID

                    exerciseItemService.saveExerciseItem(saveCommand)
                }
            }
        }
    },
)
