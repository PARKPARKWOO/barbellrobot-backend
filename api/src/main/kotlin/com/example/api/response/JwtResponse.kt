package com.example.api.response

data class JwtResponse(
    val accessToken: String,
    val refreshToken: String,
)
