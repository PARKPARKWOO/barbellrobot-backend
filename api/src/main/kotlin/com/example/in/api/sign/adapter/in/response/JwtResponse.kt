package com.example.`in`.api.sign.adapter.`in`.response

import com.example.core.sign.dto.JwtResponseDto

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
