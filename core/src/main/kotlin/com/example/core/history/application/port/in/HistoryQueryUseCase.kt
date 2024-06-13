package com.example.core.history.application.port.`in`

import com.example.core.history.application.port.query.GetHistoryMonthQuery
import com.example.core.history.dto.HistoryResponseDto
import java.util.UUID

interface HistoryQueryUseCase {
    fun getHistoryFromWeek(userId: UUID): List<HistoryResponseDto>?

    fun getHistoryFromMonth(query: GetHistoryMonthQuery): List<HistoryResponseDto>?

    fun getHistoryFromToday(userId: UUID): HistoryResponseDto?
}
