package com.example.api.sign.adapter.`in`.request

import org.jetbrains.annotations.NotNull

data class SendAuthenticationNumberRequest(
    @field:NotNull
    val email: String,
)
