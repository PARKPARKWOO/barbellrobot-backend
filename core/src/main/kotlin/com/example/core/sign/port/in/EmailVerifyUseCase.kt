package com.example.core.sign.port.`in`

import com.example.core.sign.port.`in`.command.VerifyEmailCommand
import java.util.UUID

interface EmailVerifyUseCase {
    fun verifyEmail(command: VerifyEmailCommand): UUID

    fun sendVerifyEmail(email: String)
}
