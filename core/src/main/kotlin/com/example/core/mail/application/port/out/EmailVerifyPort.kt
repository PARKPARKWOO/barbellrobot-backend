package com.example.core.mail.application.port.out

import com.example.core.mail.application.port.command.VerifyAuthenticationSuccessCommand
import com.example.core.sign.port.`in`.command.VerifyEmailCommand
import java.util.UUID

interface EmailVerifyPort {
    fun verifyEmail(command: VerifyEmailCommand): UUID

    fun saveAuthenticationNumber(email: String): Int

    fun verifyAuthenticationSuccess(command: VerifyAuthenticationSuccessCommand)
}
