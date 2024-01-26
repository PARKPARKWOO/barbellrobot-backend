package com.example.core.user.application.`in`.command

data class VerifyEmailCommand(
    val email: String,
    val authenticationNumber: Int,
)
