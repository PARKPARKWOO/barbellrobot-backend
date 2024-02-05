package com.example.core.user.member.application.service

import com.example.common.jwt.JwtResponseDto
import com.example.common.jwt.JwtTokenService
import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.user.application.`in`.command.SignInWithEmailCommand
import com.example.core.user.member.application.`in`.command.SignUpMemberFromEmailCommand
import com.example.core.user.application.out.EmailVerifyPort
import com.example.core.user.application.out.SignUpPort
import com.example.core.user.member.application.`in`.SignInMemberUseCase
import com.example.core.user.member.application.`in`.SignUpMemberUseCase
import com.example.core.user.member.application.out.MemberJpaPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberJpaPort: MemberJpaPort,
    private val emailVerifyPort: EmailVerifyPort,
    private val signUpPort: SignUpPort,
    private val jwtTokenService: JwtTokenService,
) : SignUpMemberUseCase, SignInMemberUseCase {
    @Transactional
    override fun signUpMemberFromEmail(command: SignUpMemberFromEmailCommand) {
        createVerify(command)
        emailVerifyPort.verifyAuthenticationSuccess(command.toAuthenticationCommand())
        memberJpaPort.signUpMember(command)
    }

    private fun createVerify(command: SignUpMemberFromEmailCommand) {
        verifyNickname(command.nickname)
    }

    private fun verifyNickname(nickname: String) {
        if (signUpPort.findNicknameUser(nickname)) {
            throw ServiceException(ErrorCode.DUPLICATE_NICKNAME)
        }
    }

    @Transactional(readOnly = true)
    override fun signInWithEmail(command: SignInWithEmailCommand): JwtResponseDto {
        return memberJpaPort.signInWithEmail(command)?.let { member ->
            val claims = member.getClaims()
            jwtTokenService.build(claims)
        } ?: throw ServiceException(ErrorCode.MEMBER_NOT_FOUND)
    }
}
