package com.example.core.history.port.query

import java.time.LocalDate
import java.util.UUID

data class GetHistoryQuery(
    val userId: UUID,
    val localDate: LocalDate,
)
