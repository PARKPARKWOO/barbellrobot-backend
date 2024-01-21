package com.example.core.member.application.`in`

import com.example.core.member.application.`in`.command.SendVerifyEmailCommand
import com.example.core.member.application.`in`.command.VerifyEmailCommand

interface EmailVerifyUseCase {
    fun verifyEmail(command: VerifyEmailCommand)

    fun sendVerifyEmail(command: SendVerifyEmailCommand)
}
