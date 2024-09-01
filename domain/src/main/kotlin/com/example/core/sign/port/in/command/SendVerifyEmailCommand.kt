package com.example.core.sign.port.`in`.command

data class SendVerifyEmailCommand(
    val email: String,
    val authenticationNumber: Int,
)
