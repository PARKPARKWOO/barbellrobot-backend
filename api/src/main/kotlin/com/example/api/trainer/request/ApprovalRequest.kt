package com.example.api.trainer.request

import com.example.core.managemnet.application.port.command.ApprovalCommand
import java.util.UUID

data class ApprovalRequest(
    val managementId: Long,
) {
    fun toCommand(trainerId: UUID) = ApprovalCommand(
        trainerId = trainerId,
        managementId = managementId,
    )
}
