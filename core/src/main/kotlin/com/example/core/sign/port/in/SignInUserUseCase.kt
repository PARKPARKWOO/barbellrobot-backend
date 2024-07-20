package com.example.core.sign.port.`in`

import com.example.core.sign.dto.JwtResponseDto
import com.example.core.sign.port.`in`.command.SignInWithEmailCommand
import com.example.core.sign.port.`in`.command.SignInWithKakaoCommand
import com.example.core.sign.port.`in`.query.FindUserWithSocialQuery

interface SignInUserUseCase {
    fun signInWithEmail(command: SignInWithEmailCommand): JwtResponseDto

    fun findUserWithEmail(command: SignInWithEmailCommand): Map<String, Any>

    fun signInWithKakao(command: SignInWithKakaoCommand): JwtResponseDto

    fun findUserWithSocial(query: FindUserWithSocialQuery): Map<String, Any>
}
