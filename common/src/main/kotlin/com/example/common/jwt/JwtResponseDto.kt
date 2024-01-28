package com.example.common.jwt

data class JwtResponseDto(
    val accessToken: String,
    val refreshToken: String,
)
