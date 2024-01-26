package com.example.core.user.application.`in`

import com.example.core.user.application.`in`.command.SendVerifyEmailCommand
import com.example.core.user.application.`in`.command.VerifyEmailCommand
import java.util.UUID

interface EmailVerifyUseCase {
    fun verifyEmail(command: VerifyEmailCommand): UUID

    fun sendVerifyEmail(command: SendVerifyEmailCommand)
}
