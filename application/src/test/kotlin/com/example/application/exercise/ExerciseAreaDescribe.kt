package com.example.application.exercise

import com.example.application.exercise.service.ExerciseAreaService
import com.example.core.exercise.port.out.ExerciseAreaJpaPort
import com.example.core.exercise.port.out.ItemAreaRelationshipJpaPort
import com.example.application.exercise.util.ExerciseAreaTestUtil
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk

class ExerciseAreaDescribe : DescribeSpec(
    {
        val exerciseAreaJpaPort: ExerciseAreaJpaPort = mockk()
        val itemAreaJpaPort: ItemAreaRelationshipJpaPort = mockk()

        val exerciseAreaService = ExerciseAreaService(
            exerciseAreaJpaPort = exerciseAreaJpaPort,
            itemAreaRelationshipJpaPort = itemAreaJpaPort,
        )
        describe("ExerciseArea 를 생성할때") {
            context("정상적인 요청이라면") {
                it("Success") {
                    val saveCommand = ExerciseAreaTestUtil.saveExerciseAreaCommand
                    every { exerciseAreaJpaPort.saveExerciseArea(any()) } returns Unit
                    exerciseAreaService.saveExerciseArea(saveCommand)
                }
            }
        }
    },
)
