package com.example.domain.history

import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

data class UserHistory(
    val id: UUID,
    val userId: UUID,
    val attendance: Boolean = true,
    val createdAt: LocalDate,
    val updatedAt: LocalDateTime,
)
