package com.example.`in`.api.member.response

import com.example.core.managemnet.model.Management
import com.example.core.managemnet.model.ManagementStatus
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

data class ManagementFromMemberResponse(
    val trainerId: UUID,
    val endDate: LocalDate,
    val updatedAt: LocalDateTime,
    val status: ManagementStatus,
) {
    companion object {
        @JvmStatic
        fun from(domain: Management) = ManagementFromMemberResponse(
            trainerId = domain.trainerId,
            endDate = domain.endDate,
            status = domain.status,
            updatedAt = domain.updatedAt,
        )
    }
}
