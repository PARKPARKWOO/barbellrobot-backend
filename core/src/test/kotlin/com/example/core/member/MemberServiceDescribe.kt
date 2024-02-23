package com.example.core.member

import com.example.common.jwt.JwtTokenService
import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.member.util.MemberServiceTestUtil
import com.example.core.sign.application.port.out.EmailVerifyPort
import com.example.core.user.member.application.out.MemberJpaPort
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class MemberServiceDescribe : DescribeSpec(
    {
        val memberJpaPort: MemberJpaPort = mockk()
        val emailVerifyPort: EmailVerifyPort = mockk()
        val signUpPort: SignUpPort = mockk()
        val jwtTokenService: JwtTokenService = mockk()
        val memberService = MemberService(
            memberJpaPort = memberJpaPort,
            emailVerifyPort = emailVerifyPort,
            signUpPort = signUpPort,
            jwtTokenService = jwtTokenService,
        )

        describe("signUp member with email") {
            val signUpWithEmailCommand = MemberServiceTestUtil.signUpWithEmailCommand
            context("Success Request") {
                it("SignUp Success") {
                    every { emailVerifyPort.verifyAuthenticationSuccess(any()) } returns Unit
                    every { memberJpaPort.signUpMember(any()) } returns Unit
                    every { signUpPort.findNicknameUser(any()) } returns false
                    memberService.signUpMemberFromEmail(signUpWithEmailCommand)
                }
            }
            context("Duplicate Nickname") {
                it("ServiceException ErrorCode DUPLICATE_NICKNAME") {
                    every { signUpPort.findNicknameUser(any()) } returns true
                    every { emailVerifyPort.verifyAuthenticationSuccess(any()) } returns Unit
                    val exception = shouldThrow<ServiceException> {
                        memberService.signUpMemberFromEmail(signUpWithEmailCommand)
                    }
                    exception.errorCode.name shouldBe ErrorCode.DUPLICATE_NICKNAME.name
                }
            }

            context("Authentication value is null") {
                it("ServiceException ErrorCode AUTHENTICATION_NUMBER_UN_RESOLVE") {
                    every { emailVerifyPort.verifyAuthenticationSuccess(any()) } throws ServiceException(ErrorCode.AUTHENTICATION_NUMBER_UN_RESOLVE)
                    every { signUpPort.findNicknameUser(any()) } returns false
                    val exception = shouldThrow<ServiceException> {
                        memberService.signUpMemberFromEmail(signUpWithEmailCommand)
                    }
                    exception.errorCode.name shouldBe ErrorCode.AUTHENTICATION_NUMBER_UN_RESOLVE.name
                }
            }
        }
    },
)
