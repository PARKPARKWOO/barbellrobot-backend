package com.example.core.exercise

import com.example.core.exercise.application.out.ExerciseAreaJpaPort
import com.example.core.exercise.application.out.ExerciseGoalJpaPort
import com.example.core.exercise.application.out.ExerciseItemJpaPort
import com.example.core.exercise.application.out.ItemAreaRelationshipJpaPort
import com.example.core.exercise.application.out.ItemGoalRelationshipJpaPort
import com.example.core.exercise.application.service.ExerciseItemService
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.mockk

@Suppress("UnusedPrivateProperty")
class ExerciseItemDescribe : DescribeSpec({
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

    describe("ExerciseItem create") {
        context("if area is null") {
            it("throw ServiceException"){

            }
        }

        context("if goal is null") {
            it("throw ServiceException"){

            }
        }

        context("if good request") {
            it("save success") {

            }
        }
    }
})
