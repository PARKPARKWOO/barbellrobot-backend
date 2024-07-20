package com.example.core.managemnet.model

import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

data class Management(
    val id: Long,
    val trainerId: UUID,
    val memberId: UUID,
    var status: ManagementStatus,
    val updatedAt: LocalDateTime,
    var endDate: LocalDate,
) {
    fun cancel() {
        status = ManagementStatus.CANCEL
    }

    fun reject() {
        status = ManagementStatus.REJECT
    }

    fun approval() {
        status = ManagementStatus.ACTIVE
    }

    fun offer(endDate: LocalDate) {
        status = ManagementStatus.OFFER
        this.endDate = endDate
    }
}
