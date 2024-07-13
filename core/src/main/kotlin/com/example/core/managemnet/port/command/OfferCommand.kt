package com.example.core.managemnet.port.command

import java.time.LocalDate
import java.util.UUID

data class OfferCommand(
    val memberId: UUID,
    val trainerId: UUID,
    val endDate: LocalDate,
)
