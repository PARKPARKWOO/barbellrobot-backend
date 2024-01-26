package com.example.core.member.application.out

import com.example.core.member.application.`in`.command.SendVerifyEmailCommand

interface SendEmailPort {
    fun sendAuthenticationNumber(command: SendVerifyEmailCommand)
}