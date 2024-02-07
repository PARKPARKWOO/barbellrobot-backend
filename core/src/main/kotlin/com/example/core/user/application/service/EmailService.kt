package com.example.core.user.application.service

import com.example.core.user.application.`in`.EmailVerifyUseCase
import com.example.core.user.application.`in`.command.SendVerifyEmailCommand
import com.example.core.user.application.`in`.command.VerifyEmailCommand
import com.example.core.user.application.out.EmailVerifyPort
import com.example.core.user.application.out.SendEmailPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class EmailService(
//    private val emailPort: SendEmailPort,
    private val emailVerifyPort: EmailVerifyPort,
    private val sendEmailPort: SendEmailPort,
) : EmailVerifyUseCase {
    @Transactional(readOnly = true)
    override fun verifyEmail(command: VerifyEmailCommand): UUID {
        return emailVerifyPort.verifyEmail(command)
    }

    @Transactional
    override fun sendVerifyEmail(command: SendVerifyEmailCommand) {
        emailVerifyPort.saveAuthenticationNumber(command)
        sendEmailPort.sendAuthenticationNumber(command)
    }
}
