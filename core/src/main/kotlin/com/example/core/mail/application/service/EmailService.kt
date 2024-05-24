package com.example.core.mail.application.service

import com.example.common.log.Log
import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.mail.application.port.out.EmailVerifyPort
import com.example.core.mail.application.port.out.SendEmailPort
import com.example.core.sign.application.port.`in`.EmailVerifyUseCase
import com.example.core.sign.application.port.`in`.command.SendVerifyEmailCommand
import com.example.core.sign.application.port.`in`.command.VerifyEmailCommand
import com.example.core.user.application.port.out.UserQueryPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class EmailService(
    private val emailVerifyPort: EmailVerifyPort,
    private val sendEmailPort: SendEmailPort,
    private val userQueryPort: UserQueryPort,
) : EmailVerifyUseCase, Log {
    @Transactional(readOnly = true)
    override fun verifyEmail(command: VerifyEmailCommand): UUID {
        return emailVerifyPort.verifyEmail(command)
    }

    @Transactional(readOnly = true)
    override fun sendVerifyEmail(email: String) {
        userQueryPort.findByEmail(email)?.let {
            throw ServiceException(ErrorCode.DUPLICATE_EMAIL)
        }
        val authenticationNumber = emailVerifyPort.saveAuthenticationNumber(email)
        val sendVerifyEmailCommand = SendVerifyEmailCommand(
            email = email,
            authenticationNumber = authenticationNumber,
        )
        sendEmailPort.sendAuthenticationNumber(sendVerifyEmailCommand)
    }
}
