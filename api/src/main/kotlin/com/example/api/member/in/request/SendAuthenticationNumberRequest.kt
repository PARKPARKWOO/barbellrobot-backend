package com.example.api.member.`in`.request

import com.example.core.user.application.`in`.command.SendVerifyEmailCommand
import org.jetbrains.annotations.NotNull

data class SendAuthenticationNumberRequest(
    @NotNull
    val email: String,
)

fun SendAuthenticationNumberRequest.toCommand(): SendVerifyEmailCommand {
    return SendVerifyEmailCommand(email)
}