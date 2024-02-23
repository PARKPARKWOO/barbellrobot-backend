package com.example.core.sign.application.port.`in`

import com.example.common.jwt.JwtResponseDto
import com.example.core.sign.application.port.`in`.command.SignInWithEmailCommand

interface SignInMemberUseCase {
    fun signInWithEmail(command: SignInWithEmailCommand): JwtResponseDto
}
