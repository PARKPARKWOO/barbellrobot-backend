package com.example.core.sign.application.port.`in`.command

data class SignInWithEmailCommand(
    val email: String,
    val password: String,
)
