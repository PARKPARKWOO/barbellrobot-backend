package com.example.core.history.application.port.out.command

import java.time.LocalDate
import java.util.UUID

data class AttendanceTodayCommand(
    val userId: UUID,
    val today: LocalDate,
)
