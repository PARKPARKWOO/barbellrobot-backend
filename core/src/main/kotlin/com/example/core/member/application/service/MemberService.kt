package com.example.core.member.application.service

import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.member.application.`in`.EmailVerifyUseCase
import com.example.core.member.application.`in`.SignUpUseCase
import com.example.core.member.application.`in`.command.SendVerifyEmailCommand
import com.example.core.member.application.`in`.command.SignUpMemberFromEmailCommand
import com.example.core.member.application.`in`.command.VerifyEmailCommand
import com.example.core.member.application.out.EmailVerifyPort
import com.example.core.member.application.out.MemberPort
import com.example.core.member.application.out.SendEmailPort
import com.example.core.member.application.out.SignUpPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class MemberService(
    private val signUpPort: SignUpPort,
    private val memberPort: MemberPort,
    private val emailVerifyPort: EmailVerifyPort,
    private val sendEmailPort: SendEmailPort,
) : SignUpUseCase, EmailVerifyUseCase {
    @Transactional(readOnly = true)
    override fun verifyEmail(command: VerifyEmailCommand): UUID {
        return emailVerifyPort.verifyEmail(command)
    }

    @Transactional
    override fun sendVerifyEmail(command: SendVerifyEmailCommand) {
        emailVerifyPort.saveAuthenticationNumber(command)
        sendEmailPort.sendAuthenticationNumber(command)
    }

    @Transactional
    override fun signUpMemberFromEmail(command: SignUpMemberFromEmailCommand) {
        createMemberVerify(command)
        emailVerifyPort.verifyAuthenticationSuccess(command)
        signUpPort.signUpMemberFromEmail(command)
    }

    private fun createMemberVerify(command: SignUpMemberFromEmailCommand) {
        verifyNickname(command.nickname)
    }

    private fun verifyNickname(nickname: String) {
        memberPort.getMember(nickname)?.let {
            throw ServiceException(ErrorCode.DUPLICATE_NICKNAME)
        }
    }
}
