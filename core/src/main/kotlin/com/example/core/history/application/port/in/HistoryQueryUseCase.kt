package com.example.core.history.application.port.`in`

import com.example.core.history.dto.HistoryResponseDto
import java.util.UUID

interface HistoryQueryUseCase {
    fun getHistoryFromWeek(userId: UUID): List<HistoryResponseDto>?

    fun getHistoryFromMonth(userId: UUID): List<HistoryResponseDto>?
}
