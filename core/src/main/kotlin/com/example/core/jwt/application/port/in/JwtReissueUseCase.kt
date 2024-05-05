package com.example.core.jwt.application.port.`in`

import com.example.common.jwt.JwtResponseDto

interface JwtReissueUseCase {
    fun reissueToken(refreshToken: String): JwtResponseDto
}
