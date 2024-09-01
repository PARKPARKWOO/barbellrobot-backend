package com.example.core.sign.dto

data class JwtResponseDto(
    val accessToken: String,
    val refreshToken: String,
)
