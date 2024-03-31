package com.example.core.sign

import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.managemnet.application.port.`in`.ManagementUseCase
import com.example.core.sign.application.port.out.EmailVerifyPort
import com.example.core.sign.application.service.SignUpTrainerService
import com.example.core.sign.util.MemberServiceTestUtil
import com.example.core.sign.util.TrainerServiceTestUtil
import com.example.core.user.application.port.out.UserQueryPort
import com.example.core.user.trainer.application.port.out.TrainerJpaPort
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class SignUpTrainerDescribe : DescribeSpec(
    {
        val userQueryPort: UserQueryPort = mockk()
        val trainerJpaPort: TrainerJpaPort = mockk()
        val emailVerifyPort: EmailVerifyPort = mockk()
        val managementUseCase: ManagementUseCase = mockk()
        val signUpTrainerService = SignUpTrainerService(
            trainerJpaPort = trainerJpaPort,
            userQueryPort = userQueryPort,
            emailVerifyPort = emailVerifyPort,
            managementUseCase = managementUseCase,
        )
        describe("signUp trainer with email") {
            val signUpWithEmailCommand = TrainerServiceTestUtil.signUpWithEmailCommand
            context("Success Request") {
                it("SignUp Success") {
                    every { emailVerifyPort.verifyAuthenticationSuccess(any()) } returns Unit
                    every { trainerJpaPort.signUpTrainer(any()) } returns TrainerServiceTestUtil.trainer
                    every { userQueryPort.findByNickname(any()) } returns null
                    every { managementUseCase.create(any()) } returns Unit
                    signUpTrainerService.signUpWithEmail(signUpWithEmailCommand)
                }
            }
            context("Duplicate Nickname") {
                it("ServiceException ErrorCode DUPLICATE_NICKNAME") {
                    every { userQueryPort.findByNickname(any()) } returns MemberServiceTestUtil.memberEntity
                    every { emailVerifyPort.verifyAuthenticationSuccess(any()) } returns Unit
                    val exception = shouldThrow<ServiceException> {
                        signUpTrainerService.signUpWithEmail(signUpWithEmailCommand)
                    }
                    exception.errorCode.name shouldBe ErrorCode.DUPLICATE_NICKNAME.name
                }
            }

            context("Authentication value is null") {
                it("ServiceException ErrorCode AUTHENTICATION_NUMBER_UN_RESOLVE") {
                    every { emailVerifyPort.verifyAuthenticationSuccess(any()) } throws ServiceException(ErrorCode.AUTHENTICATION_NUMBER_UN_RESOLVE)
                    every { userQueryPort.findByNickname(any()) } returns null
                    val exception = shouldThrow<ServiceException> {
                        signUpTrainerService.signUpWithEmail(signUpWithEmailCommand)
                    }
                    exception.errorCode.name shouldBe ErrorCode.AUTHENTICATION_NUMBER_UN_RESOLVE.name
                }
            }
        }
    },
)
