package com.example.core.sign.port.`in`.command

data class VerifyEmailCommand(
    val email: String,
    val authenticationNumber: Int,
)
