package com.example.`in`.api.member.request

import com.example.core.managemnet.port.command.CancelCommand
import java.util.UUID

data class CancelManagementRequest(
    val managementId: Long,
) {
    fun toCommand(memberId: UUID) = CancelCommand(
        memberId = memberId,
        managementId = managementId,
    )
}
