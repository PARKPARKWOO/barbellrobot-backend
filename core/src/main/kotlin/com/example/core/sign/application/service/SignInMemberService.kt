package com.example.core.sign.application.service

import com.example.common.jwt.JwtResponseDto
import com.example.common.jwt.JwtTokenService
import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.sign.application.port.`in`.SignInMemberUseCase
import com.example.core.sign.application.port.`in`.command.SignInWithEmailCommand
import com.example.core.user.member.application.out.MemberJpaPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SignInMemberService(
    private val memberJpaPort: MemberJpaPort,
    private val jwtTokenService: JwtTokenService,
) : SignInMemberUseCase {
    @Transactional(readOnly = true)
    override fun signInWithEmail(command: SignInWithEmailCommand): JwtResponseDto {
        return memberJpaPort.signInWithEmail(command)?.let { member ->
            val claims = member.getClaims()
            jwtTokenService.build(claims)
        } ?: throw ServiceException(ErrorCode.MEMBER_NOT_FOUND)
    }
}
