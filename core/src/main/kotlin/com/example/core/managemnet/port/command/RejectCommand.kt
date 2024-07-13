package com.example.core.managemnet.port.command

import java.util.UUID

data class RejectCommand(
    val trainerId: UUID,
    val managementId: Long,
)
