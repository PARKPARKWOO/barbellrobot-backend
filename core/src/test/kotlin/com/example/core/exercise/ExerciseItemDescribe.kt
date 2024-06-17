package com.example.core.exercise

import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.common.util.Tx
import com.example.core.exercise.application.port.command.SaveExerciseItemCommand
import com.example.core.exercise.application.port.out.ExerciseAreaJpaPort
import com.example.core.exercise.application.port.out.ExerciseGoalJpaPort
import com.example.core.exercise.application.port.out.ExerciseItemJpaPort
import com.example.core.exercise.application.port.out.ItemAreaRelationshipJpaPort
import com.example.core.exercise.application.port.out.ItemGoalRelationshipJpaPort
import com.example.core.exercise.application.service.ExerciseItemService
import com.example.core.exercise.util.ExerciseAreaTestUtil
import com.example.core.exercise.util.ExerciseGoalTestUtil
import com.example.core.multimedia.application.port.`in`.MultimediaUploadUseCase
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.Runs
import io.mockk.called
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.runBlocking

@Suppress("UnusedPrivateProperty")
class ExerciseItemDescribe : DescribeSpec(
    {
        val itemAreaRelationshipJpaPort: ItemAreaRelationshipJpaPort = mockk()
        val itemGoalRelationshipJpaPort: ItemGoalRelationshipJpaPort = mockk()
        val exerciseAreaJpaPort: ExerciseAreaJpaPort = mockk()
        val exerciseGoalJpaPort: ExerciseGoalJpaPort = mockk()
        val multimediaUploadUseCase: MultimediaUploadUseCase = mockk()
        val txAdvice = mockk<Tx.TxAdvice>()
        val tx = Tx(txAdvice)
        val exerciseItemJpaPort: ExerciseItemJpaPort = mockk()
        val exerciseItemService = ExerciseItemService(
            exerciseAreaJpaPort = exerciseAreaJpaPort,
            exerciseGoalJpaPort = exerciseGoalJpaPort,
            exerciseItemJpaPort = exerciseItemJpaPort,
            itemAreaRelationshipJpaPort = itemAreaRelationshipJpaPort,
            itemGoalRelationshipJpaPort = itemGoalRelationshipJpaPort,
            multimediaUploadUseCase = multimediaUploadUseCase,
        )

        describe("saveExerciseItem") {
            val command = SaveExerciseItemCommand(
                exerciseGoals = mutableListOf(1L),
                exerciseAreas = mutableListOf(1L),
                image = null,
                video = null,
                exerciseName = "",
            )
            context("when successful") {
                // 테스트 실행 전에 Mock 동작을 설정합니다.
                beforeEach {
                    coEvery { exerciseGoalJpaPort.getExerciseGoals(any()) } returns listOf(ExerciseGoalTestUtil.entity)
                    coEvery { exerciseAreaJpaPort.getExerciseAreas(any()) } returns listOf(ExerciseAreaTestUtil.entity)
                    coEvery { multimediaUploadUseCase.uploadMultipartFiles(any()) } returns listOf("url")
                    coEvery { exerciseItemJpaPort.saveExerciseItem(any()) } returns 1L
                    coEvery { itemGoalRelationshipJpaPort.addRelationship(any()) } just Runs
                    coEvery { itemAreaRelationshipJpaPort.addRelationship(any()) } just Runs
                    coEvery { txAdvice.read(any<() -> Any>()) } answers {
                        firstArg<() -> Any>().invoke()
                    }
                    coEvery { txAdvice.write(any<() -> Any>()) } answers {
                        firstArg<() -> Any>().invoke()
                    }
                }

                it("should save exercise item successfully") {

                    // 실행
                    runBlocking {
                        exerciseItemService.saveExerciseItem(command)
                    }

                    // 검증
                    coVerify { exerciseGoalJpaPort.getExerciseGoals(listOf(1L)) }
                    coVerify { exerciseAreaJpaPort.getExerciseAreas(listOf(1L)) }
                    coVerify { exerciseItemJpaPort.saveExerciseItem(any()) }
                    coVerify { itemGoalRelationshipJpaPort.addRelationship(any()) }
                    coVerify { itemAreaRelationshipJpaPort.addRelationship(any()) }
                }
            }

            context("when goal not found") {
                beforeEach {
                    coEvery { exerciseGoalJpaPort.getExerciseGoals(any()) } returns null
                    coEvery { exerciseAreaJpaPort.getExerciseAreas(any()) } shouldNotBe called
                }

                it("should throw ServiceException for missing goal and not call getExerciseAreas") {
                    val exception = shouldThrow<ServiceException> {
                        runBlocking {
                            exerciseItemService.saveExerciseItem(command)
                        }
                    }

                    exception.errorCode shouldBe ErrorCode.NOT_FOUND_EXERCISE_GOAL
                }
            }

            context("when area not found") {
                beforeEach {
                    coEvery { exerciseAreaJpaPort.getExerciseAreas(any()) } returns null
                    coEvery { exerciseGoalJpaPort.getExerciseGoals(any()) } returns listOf(ExerciseGoalTestUtil.entity)
                }

                it("should throw ServiceException for missing area") {
                    shouldThrow<ServiceException> {
                        runBlocking {
                            exerciseItemService.saveExerciseItem(command)
                        }
                    }.errorCode shouldBe ErrorCode.NOT_FOUND_EXERCISE_AREA
                }
            }
        }
    },
)
