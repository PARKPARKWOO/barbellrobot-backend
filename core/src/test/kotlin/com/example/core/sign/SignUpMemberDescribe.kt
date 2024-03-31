package com.example.core.sign

import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.sign.application.port.out.EmailVerifyPort
import com.example.core.sign.application.service.SignUpMemberService
import com.example.core.sign.util.MemberServiceTestUtil
import com.example.core.user.application.port.out.UserQueryPort
import com.example.core.user.member.application.out.MemberJpaPort
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class SignUpMemberDescribe : DescribeSpec(
    {
        val memberJpaPort: MemberJpaPort = mockk()
        val emailVerifyPort: EmailVerifyPort = mockk()
        val userQueryPort: UserQueryPort = mockk()
        val memberService = SignUpMemberService(
            memberJpaPort = memberJpaPort,
            emailVerifyPort = emailVerifyPort,
            userQueryPort = userQueryPort,
        )

        describe("signUp member with email") {
            val signUpWithEmailCommand = MemberServiceTestUtil.signUpWithEmailCommand
            context("Success Request") {
                it("SignUp Success") {
                    every { emailVerifyPort.verifyAuthenticationSuccess(any()) } returns Unit
                    every { memberJpaPort.signUpMember(any()) } returns Unit
                    every { userQueryPort.findByNickname(any()) } returns null
                    memberService.signUpWithEmail(signUpWithEmailCommand)
                }
            }
            context("Duplicate Nickname") {
                it("ServiceException ErrorCode DUPLICATE_NICKNAME") {
                    every { userQueryPort.findByNickname(any()) } returns MemberServiceTestUtil.memberEntity
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
