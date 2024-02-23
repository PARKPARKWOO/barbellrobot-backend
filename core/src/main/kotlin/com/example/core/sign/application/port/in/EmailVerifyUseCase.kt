package com.example.core.sign.application.port.`in`

import com.example.core.sign.application.port.`in`.command.SendVerifyEmailCommand
import com.example.core.sign.application.port.`in`.command.VerifyEmailCommand
import java.util.UUID

interface EmailVerifyUseCase {
    fun verifyEmail(command: VerifyEmailCommand): UUID

    fun sendVerifyEmail(command: SendVerifyEmailCommand)
}
