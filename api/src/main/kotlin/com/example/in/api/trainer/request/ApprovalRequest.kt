package com.example.`in`.api.trainer.request

import com.example.core.managemnet.port.command.ApprovalCommand
import java.util.UUID

data class ApprovalRequest(
    val managementId: Long,
) {
    fun toCommand(trainerId: UUID) = ApprovalCommand(
        trainerId = trainerId,
        managementId = managementId,
    )
}
