package com.example.api.email.`in`.request

import com.example.core.user.application.`in`.command.SignInWithEmailCommand

data class SignInWithEmailRequest(
    val email: String,
    val password: String,
) {
    fun toCommand(): SignInWithEmailCommand {
        return SignInWithEmailCommand(
            email = email,
            password = password,
        )
    }
}
