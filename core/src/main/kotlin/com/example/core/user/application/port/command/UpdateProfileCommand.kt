package com.example.core.user.application.port.command

import java.util.UUID

data class UpdateProfileCommand(
    val userId: UUID,
    val uri: String,
)
