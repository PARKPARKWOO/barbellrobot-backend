package com.example.core.sign.application.service

import com.example.common.jwt.JwtResponseDto
import com.example.core.sign.application.port.`in`.SignInUserUseCase
import com.example.core.sign.application.port.`in`.command.SignInWithEmailCommand

abstract class AbstractSignInService : SignInUserUseCase {
    abstract override fun signInWithEmail(command: SignInWithEmailCommand): JwtResponseDto
}
