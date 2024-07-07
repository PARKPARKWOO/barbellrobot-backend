package com.example.core.sign.application.port.`in`.command

import com.example.core.user.model.Provider

data class SignUpWithSocialCommand(
    val provider: Provider,
)
