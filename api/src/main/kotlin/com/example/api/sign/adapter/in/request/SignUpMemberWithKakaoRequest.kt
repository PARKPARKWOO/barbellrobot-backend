package com.example.api.sign.adapter.`in`.request

data class SignUpMemberWithKakaoRequest(
    val authorizationCode: String,
    val redirectUri: String,
)
