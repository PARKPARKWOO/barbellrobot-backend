package com.example.core.user.member.application.`in`

import com.example.common.jwt.JwtResponseDto
import com.example.core.user.application.`in`.command.SignInWithEmailCommand

interface SignInMemberUseCase {
    fun signInWithEmail(command: SignInWithEmailCommand): JwtResponseDto
}
