package com.example.core.user.member.application.service

import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.user.application.`in`.command.SignUpMemberFromEmailCommand
import com.example.core.user.application.out.EmailVerifyPort
import com.example.core.user.application.out.SendEmailPort
import com.example.core.user.application.out.SignUpPort
import com.example.core.user.member.application.`in`.SignUpMemberUseCase
import com.example.core.user.member.application.out.MemberPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberPort: MemberPort,
    private val emailVerifyPort: EmailVerifyPort,
    private val signUpPort: SignUpPort,
) : SignUpMemberUseCase {
    @Transactional
    override fun signUpMemberFromEmail(command: SignUpMemberFromEmailCommand) {
        createVerify(command)
        emailVerifyPort.verifyAuthenticationSuccess(command.toAuthenticationCommand())
        memberPort.signUpMember(command)
    }

    private fun createVerify(command: SignUpMemberFromEmailCommand) {
        verifyNickname(command.nickname)
    }

    private fun verifyNickname(nickname: String) {
        if (signUpPort.findNicknameUser(nickname)) {
            throw ServiceException(ErrorCode.DUPLICATE_NICKNAME)
        }
    }
}
