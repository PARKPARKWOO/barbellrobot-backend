package com.example.core.user.port.command

import java.util.UUID

data class UpdateProfileCommand(
    val userId: UUID,
    val uri: String,
)
