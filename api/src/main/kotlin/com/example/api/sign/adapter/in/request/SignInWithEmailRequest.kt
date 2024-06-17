package com.example.api.sign.adapter.`in`.request

import com.example.core.sign.application.port.`in`.command.SignInWithEmailCommand
import jakarta.validation.constraints.NotBlank

data class SignInWithEmailRequest(
    @field:NotBlank
    val email: String,
    @field:NotBlank
    val password: String,
) {
    fun toCommand(): SignInWithEmailCommand {
        return SignInWithEmailCommand(
            email = email,
            password = password,
        )
    }
}
