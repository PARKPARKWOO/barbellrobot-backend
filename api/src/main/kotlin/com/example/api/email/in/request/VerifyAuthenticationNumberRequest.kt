package com.example.api.email.`in`.request

import com.example.core.user.application.`in`.command.VerifyEmailCommand

data class VerifyAuthenticationNumberRequest(
    val email: String,
    val authenticationNumber: Int,
)

fun VerifyAuthenticationNumberRequest.toCommand(): VerifyEmailCommand {
    return VerifyEmailCommand(
        email = email,
        authenticationNumber = authenticationNumber,
    )
}
