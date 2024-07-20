package com.example.application.mail

import com.example.application.common.log.Log
import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.mail.application.port.out.EmailVerifyPort
import com.example.core.mail.application.port.out.SendEmailPort
import com.example.core.sign.port.`in`.EmailVerifyUseCase
import com.example.core.sign.port.`in`.command.SendVerifyEmailCommand
import com.example.core.sign.port.`in`.command.VerifyEmailCommand
import com.example.core.user.port.out.UserQueryPort
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
