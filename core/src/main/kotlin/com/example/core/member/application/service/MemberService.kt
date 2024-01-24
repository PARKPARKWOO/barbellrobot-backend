package com.example.core.member.application.service

import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.member.application.`in`.EmailVerifyUseCase
import com.example.core.member.application.`in`.SignUpUseCase
import com.example.core.member.application.`in`.command.SendVerifyEmailCommand
import com.example.core.member.application.`in`.command.SignMemberFromEmailCommand
import com.example.core.member.application.`in`.command.VerifyEmailCommand
import com.example.core.member.application.out.EmailVerifyPort
import com.example.core.member.application.out.MemberPort
import com.example.core.member.application.out.SignUpPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class MemberService(
    private val signUpPort: SignUpPort,
    private val memberPort: MemberPort,
    private val emailVerifyPort: EmailVerifyPort,
) : SignUpUseCase, EmailVerifyUseCase {
    @Transactional(readOnly = true)
    override fun verifyEmail(command: VerifyEmailCommand) {
        emailVerifyPort.verifyEmail(command)
    }

    @Transactional
    override fun sendVerifyEmail(command: SendVerifyEmailCommand) {
        emailVerifyPort.sendVerifyEmail(command)
    }

    @Transactional
    override fun signMemberFromEmail(command: SignMemberFromEmailCommand): UUID {
        createMemberVerify(command)
        return signUpPort.signMemberFromEmail(command)
    }

    private fun createMemberVerify(command: SignMemberFromEmailCommand) {
        verifyNickname(command.nickname)
    }

    private fun verifyNickname(nickname: String) {
        memberPort.getMember(nickname)?.let {
            throw ServiceException(ErrorCode.DUPLICATE_NICKNAME)
        }
    }
}
