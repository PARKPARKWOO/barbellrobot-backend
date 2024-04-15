package com.example.core.sign.application.port.`in`.command

data class SendVerifyEmailCommand(
    val email: String,
    val authenticationNumber: Int,
)
