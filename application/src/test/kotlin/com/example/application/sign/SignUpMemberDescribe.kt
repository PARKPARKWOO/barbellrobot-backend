package com.example.application.sign

import com.example.application.common.transaction.Tx
import com.example.application.sign.util.MemberServiceTestUtil
import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.mail.application.port.out.EmailVerifyPort
import com.example.core.rival.port.`in`.RivalUseCase
import com.example.core.transaction.TransactionManager
import com.example.core.user.model.Gender
import com.example.core.user.port.command.SaveMemberInfoCommand
import com.example.core.user.port.`in`.MemberInfoUseCase
import com.example.core.user.port.out.MemberJpaPort
import com.example.core.user.port.out.UserQueryPort
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

@Suppress("UnusedPrivateProperty")
class SignUpMemberDescribe : DescribeSpec(
    {
        val memberJpaPort: MemberJpaPort = mockk()
        val emailVerifyPort: EmailVerifyPort = mockk()
        val userQueryPort: UserQueryPort = mockk()
        val memberInfoUseCase: MemberInfoUseCase = mockk()
        val rivalUseCase: RivalUseCase = mockk()
        val txAdvice = mockk<TransactionManager>()
        val tx = Tx(txAdvice)
        val memberService = SignUpMemberService(
            memberJpaPort = memberJpaPort,
            emailVerifyPort = emailVerifyPort,
            userQueryPort = userQueryPort,
            memberInfoUseCase = memberInfoUseCase,
            rivalUseCase = rivalUseCase,
        )

        describe("signUp member with email") {
            val signUpWithEmailCommand = MemberServiceTestUtil.signUpWithEmailCommand
            context("Success Request") {
                this@context.beforeEach {
                    every { memberInfoUseCase.save(any()) } returns Unit
                }
                it("SignUp Success") {
                    every { emailVerifyPort.verifyAuthenticationSuccess(any()) } returns Unit
                    every { memberJpaPort.signUpWithEmailMember(any()) } returns MemberServiceTestUtil.member.id
                    every { userQueryPort.findByNickname(any()) } returns null
                    every { txAdvice.writeTx(any<() -> Any>()) } answers {
                        firstArg<() -> Any>().invoke()
                    }
                    every { rivalUseCase.createRival(any()) } returns Unit
//                    every { txAdvice.write(any<() -> Unit>()) } answers {
//                        firstArg<() -> Unit>().invoke()
//                    }
                    val command = SaveMemberInfoCommand(
                        memberId = MemberServiceTestUtil.member.id,
                        nickname = "nickname",
                        tall = 1.1,
                        weight = 1.1,
                        skeletalMuscleMass = null,
                        gender = Gender.FEMALE,
                        age = 1,
                        exerciseMonths = 1,
                    )
                    // Act
                    memberService.signUpWithEmail(signUpWithEmailCommand)
                    // Assert
                    verify { memberInfoUseCase.save(command) }
                }
            }
            context("Duplicate Nickname") {
                it("ServiceException ErrorCode DUPLICATE_NICKNAME") {
                    every { userQueryPort.findByNickname(any()) } returns MemberServiceTestUtil.memberInfo
                    every { emailVerifyPort.verifyAuthenticationSuccess(any()) } returns Unit
                    val exception = shouldThrow<ServiceException> {
                        memberService.signUpWithEmail(signUpWithEmailCommand)
                    }
                    exception.errorCode.name shouldBe ErrorCode.DUPLICATE_NICKNAME.name
                }
            }

            context("Authentication value is null") {
                it("ServiceException ErrorCode AUTHENTICATION_NUMBER_UN_RESOLVE") {
                    every { emailVerifyPort.verifyAuthenticationSuccess(any()) } throws ServiceException(ErrorCode.AUTHENTICATION_NUMBER_UN_RESOLVE)
                    every { userQueryPort.findByNickname(any()) } returns null
                    val exception = shouldThrow<ServiceException> {
                        memberService.signUpWithEmail(signUpWithEmailCommand)
                    }
                    exception.errorCode.name shouldBe ErrorCode.AUTHENTICATION_NUMBER_UN_RESOLVE.name
                }
            }
        }
    },
)
