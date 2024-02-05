package com.example.api.member.`in`.reqeust

data class SignUpMemberWithKakaoRequest(
    val authorizationCode: String,
    val redirectUri: String,
)
