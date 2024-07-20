package com.example.core.sign.port.`in`

import com.example.core.sign.dto.JwtResponseDto

interface JwtReissueUseCase {
    fun reissueToken(refreshToken: String): JwtResponseDto
}
