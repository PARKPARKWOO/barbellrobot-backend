package com.example.core.user.member.application.command

import com.example.domain.user.SocialProvider

data class SaveMemberCommand(
    val email: String?,
    val password: String?,
    val socialProvider: SocialProvider?,
)
