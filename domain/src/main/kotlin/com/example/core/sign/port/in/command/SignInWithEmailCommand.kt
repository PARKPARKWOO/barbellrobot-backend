package com.example.core.sign.port.`in`.command

data class SignInWithEmailCommand(
    val email: String,
    val password: String,
)
