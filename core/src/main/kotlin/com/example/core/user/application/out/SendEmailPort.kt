package com.example.core.user.application.out

import com.example.core.user.application.`in`.command.SendVerifyEmailCommand

interface SendEmailPort {
    fun sendAuthenticationNumber(command: SendVerifyEmailCommand)
}
