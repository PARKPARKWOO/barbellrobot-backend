package com.example.core.managemnet.application.port.command

import java.util.UUID

data class ApprovalCommand(
    val trainerId: UUID,
    val managementId: Long,
)
