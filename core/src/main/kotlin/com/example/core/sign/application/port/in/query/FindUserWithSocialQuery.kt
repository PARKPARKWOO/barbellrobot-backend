package com.example.core.sign.application.port.`in`.query

import com.example.domain.user.Provider

data class FindUserWithSocialQuery(
    // social id
    val id: String,
    val provider: Provider,
)
