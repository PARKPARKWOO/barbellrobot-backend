package com.example.`in`.api.sign.adapter.`in`.request

import com.example.core.sign.port.`in`.command.SignInWithKakaoCommand

data class SignInWithKakaoRequest(
    val accessToken: String,
) {
    fun toCommand(): SignInWithKakaoCommand = SignInWithKakaoCommand(
        accessToken = accessToken,
    )
}
