package com.example.core.managemnet.port.command

import java.util.UUID

data class CancelCommand(
    val memberId: UUID,
    val managementId: Long,
)
