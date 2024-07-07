package com.example.core.sign.application.port.`in`.query

import com.example.core.user.model.Provider

data class FindUserWithSocialQuery(
    // social id
    val id: String,
    val provider: Provider,
)
