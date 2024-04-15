package com.example.api.sign.adapter.`in`.request

import com.example.core.sign.application.port.`in`.command.SendVerifyEmailCommand
import org.jetbrains.annotations.NotNull

data class SendAuthenticationNumberRequest(
    @field:NotNull
    val email: String,
)
