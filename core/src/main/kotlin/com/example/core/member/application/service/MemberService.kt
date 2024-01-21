package com.example.core.member.application.service

import com.example.core.member.application.`in`.EmailVerifyUseCase
import com.example.core.member.application.`in`.SignUpUseCase
import com.example.core.member.application.`in`.command.SendVerifyEmailCommand
import com.example.core.member.application.`in`.command.VerifyEmailCommand
import com.example.core.member.application.out.EmailVerifyPort
import com.example.core.member.application.out.SignUpPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val signUpPort: SignUpPort,
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
    override fun signMemberFromEmail() {
        TODO("Not yet implemented")
    }
}
