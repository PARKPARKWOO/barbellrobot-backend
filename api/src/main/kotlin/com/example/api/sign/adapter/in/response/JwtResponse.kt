package com.example.api.sign.adapter.`in`.response

import com.example.common.jwt.JwtResponseDto

data class JwtResponse(
    val accessToken: String,
    val refreshToken: String,
) {
    companion object {
        fun from(dto: JwtResponseDto) = JwtResponse(
            accessToken = dto.accessToken,
            refreshToken = dto.refreshToken,
        )
    }
}
