package com.example.core.pt.command

import java.util.UUID

data class GeneratePtCommand(
    val memberId: UUID,
    val time: Int,
    val day: Int,
)
