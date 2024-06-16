package com.example.core.sign.application.port.`in`.command

import com.example.domain.user.Provider

data class SignUpWithSocialCommand(
    val provider: Provider,
)
