package com.example.api.email.`in`.request

import com.example.core.user.application.`in`.command.VerifyEmailCommand
import org.jetbrains.annotations.NotNull

data class VerifyAuthenticationNumberRequest(
    @field:NotNull
    val email: String,
    @field:NotNull
    val authenticationNumber: Int,
)

fun VerifyAuthenticationNumberRequest.toCommand(): VerifyEmailCommand {
    return VerifyEmailCommand(
        email = email,
        authenticationNumber = authenticationNumber,
    )
}
