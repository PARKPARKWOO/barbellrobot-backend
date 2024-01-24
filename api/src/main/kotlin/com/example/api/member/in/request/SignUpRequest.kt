package com.example.api.member.`in`.request

import org.jetbrains.annotations.NotNull

data class SignUpRequest(
    @NotNull
    val nickname: String,
    @NotNull
    val email: String,
    @NotNull
    val password: String,
)
