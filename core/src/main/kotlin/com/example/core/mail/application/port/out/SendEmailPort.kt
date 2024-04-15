package com.example.core.mail.application.port.out

import com.example.core.sign.application.port.`in`.command.SendVerifyEmailCommand

interface SendEmailPort {
    fun sendAuthenticationNumber(command: SendVerifyEmailCommand)
}
