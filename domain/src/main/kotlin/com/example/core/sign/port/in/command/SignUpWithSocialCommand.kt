package com.example.core.sign.port.`in`.command

import com.example.core.user.model.Provider

data class SignUpWithSocialCommand(
    val provider: Provider,
)
