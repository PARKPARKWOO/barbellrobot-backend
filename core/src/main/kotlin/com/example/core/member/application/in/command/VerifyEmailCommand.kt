package com.example.core.member.application.`in`.command

data class VerifyEmailCommand(
    val email: String,
    val authenticationNumber: Int,
)
