package com.example.core.sign.port.`in`

import com.example.core.sign.port.`in`.command.SignUpUserWithEmailCommand

interface SignUpUserUseCase {
    fun signUpWithEmail(command: SignUpUserWithEmailCommand)

    fun saveUser(command: SignUpUserWithEmailCommand)
}
