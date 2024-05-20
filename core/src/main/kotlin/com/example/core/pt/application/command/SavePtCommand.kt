package com.example.core.pt.application.command

import java.util.UUID

data class SavePtCommand(
    val memberId: UUID,
    val content: String,
)
