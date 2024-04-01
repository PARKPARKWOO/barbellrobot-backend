package com.example.api.trainer.request

import com.example.core.managemnet.application.port.command.RejectCommand
import java.util.UUID

data class RejectRequest(
    val managementId: Long,
) {
    fun toCommand(trainerId: UUID) = RejectCommand(
        trainerId = trainerId,
        managementId = managementId,
    )
}
