package com.example.core.user.member.application.command

import java.util.UUID

data class UpdateNicknameCommand(
    val id: UUID,
    val nickname: String,
)
