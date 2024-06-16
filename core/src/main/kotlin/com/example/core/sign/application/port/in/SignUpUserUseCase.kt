package com.example.core.sign.application.port.`in`

import com.example.core.sign.application.port.SignUpUserWithEmailCommand

interface SignUpUserUseCase {
    fun signUpWithEmail(command: SignUpUserWithEmailCommand)

    fun saveUser(command: SignUpUserWithEmailCommand)
}
