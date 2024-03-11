package com.example.core.history.application.port.out.query

import java.time.LocalDate
import java.util.UUID

data class FindUserHistoryQuery(
    val userId: UUID,
    val startDate: LocalDate,
    val endDate: LocalDate,
)
