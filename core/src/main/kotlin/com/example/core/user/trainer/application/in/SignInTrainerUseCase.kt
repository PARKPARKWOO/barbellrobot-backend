package com.example.core.user.trainer.application.`in`

import com.example.common.jwt.JwtResponseDto
import com.example.core.user.application.`in`.command.SignInWithEmailCommand

interface SignInTrainerUseCase {
    fun signInWithEmail(command: SignInWithEmailCommand): JwtResponseDto
}
