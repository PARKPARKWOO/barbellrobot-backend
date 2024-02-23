package com.example.core.sign.application.port.out

import com.example.core.sign.application.port.`in`.command.SendVerifyEmailCommand
import com.example.core.sign.application.port.`in`.command.VerifyEmailCommand
import com.example.core.sign.application.port.out.command.VerifyAuthenticationSuccessCommand
import java.util.UUID

interface EmailVerifyPort {
    fun verifyEmail(command: VerifyEmailCommand): UUID

    fun saveAuthenticationNumber(command: SendVerifyEmailCommand)

    fun verifyAuthenticationSuccess(command: VerifyAuthenticationSuccessCommand)
}
