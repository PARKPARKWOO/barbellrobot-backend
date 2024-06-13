package com.example.core.history.application.port.query

import java.time.LocalDate
import java.util.UUID

data class GetHistoryMonthQuery(
    val userId: UUID,
    val localDate: LocalDate,
)
