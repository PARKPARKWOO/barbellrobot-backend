package com.example.api.sign.adapter.`in`.request

import com.example.core.sign.application.port.`in`.command.SignInWithKakaoCommand

data class SignInWithKakaoRequest(
    val accessToken: String,
) {
    fun toCommand(): SignInWithKakaoCommand = SignInWithKakaoCommand(
        accessToken = accessToken,
    )
}
