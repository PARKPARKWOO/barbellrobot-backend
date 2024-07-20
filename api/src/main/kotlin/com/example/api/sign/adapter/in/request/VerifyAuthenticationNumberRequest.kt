package com.example.api.sign.adapter.`in`.request

import com.example.core.sign.port.`in`.command.VerifyEmailCommand
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
