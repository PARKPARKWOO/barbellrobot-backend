package com.example.core.user.application.out

import com.example.core.user.application.`in`.command.SendVerifyEmailCommand
import com.example.core.user.application.`in`.command.VerifyEmailCommand
import com.example.core.user.application.out.command.VerifyAuthenticationSuccessCommand
import java.util.UUID

interface EmailVerifyPort {
    fun verifyEmail(command: VerifyEmailCommand): UUID

    fun saveAuthenticationNumber(command: SendVerifyEmailCommand)

    fun verifyAuthenticationSuccess(command: VerifyAuthenticationSuccessCommand)
}
