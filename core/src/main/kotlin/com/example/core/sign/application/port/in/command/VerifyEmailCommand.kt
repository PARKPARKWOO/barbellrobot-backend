package com.example.core.sign.application.port.`in`.command

data class VerifyEmailCommand(
    val email: String,
    val authenticationNumber: Int,
)
