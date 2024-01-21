package com.example.core.member.application.out

import com.example.core.member.application.`in`.command.SendVerifyEmailCommand
import com.example.core.member.application.`in`.command.VerifyEmailCommand

interface EmailVerifyPort {
    fun verifyEmail(command: VerifyEmailCommand)

    fun sendVerifyEmail(command: SendVerifyEmailCommand)
}
