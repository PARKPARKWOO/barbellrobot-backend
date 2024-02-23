package com.example.api.sign.adapter.`in`.request

import com.example.core.sign.application.port.`in`.command.SignInWithEmailCommand
import org.jetbrains.annotations.NotNull

data class SignInWithEmailRequest(
    @field:NotNull
    val email: String,
    @field:NotNull
    val password: String,
) {
    fun toCommand(): SignInWithEmailCommand {
        return SignInWithEmailCommand(
            email = email,
            password = password,
        )
    }
}
