package com.example.api.member.adapter.`in`.reqeust

data class SignUpMemberWithKakaoRequest(
    val authorizationCode: String,
    val redirectUri: String,
)
