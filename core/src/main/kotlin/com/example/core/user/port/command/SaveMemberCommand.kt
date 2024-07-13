package com.example.core.user.port.command

import com.example.core.user.model.SocialProvider

data class SaveMemberCommand(
    val email: String?,
    val password: String?,
    val socialProvider: SocialProvider?,
)
