package com.example.domain.user

enum class Provider {
    KAKAO,
}

data class SocialProvider(
    val socialId: String,
    val provider: Provider,
)
