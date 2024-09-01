package com.example.core.user.port.command

import java.util.UUID

data class UpdateNicknameCommand(
    val id: UUID,
    val nickname: String,
)
