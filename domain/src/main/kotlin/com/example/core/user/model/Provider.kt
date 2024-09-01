package com.example.core.user.model

enum class Provider {
    KAKAO,
}

data class SocialProvider(
    val socialId: String,
    val provider: Provider,
)
