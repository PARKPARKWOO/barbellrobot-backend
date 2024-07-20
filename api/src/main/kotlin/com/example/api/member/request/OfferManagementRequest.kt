package com.example.api.member.request

import com.example.core.managemnet.port.command.OfferCommand
import java.time.LocalDate
import java.util.UUID

data class OfferManagementRequest(
    val trainerId: UUID,
    val endDate: LocalDate,
) {
    fun toCommand(memberId: UUID) = OfferCommand(
        memberId = memberId,
        trainerId = trainerId,
        endDate = endDate,
    )
}
