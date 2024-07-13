package com.example.core.history.port.`in`

import com.example.core.history.port.query.GetHistoryQuery
import com.example.core.history.dto.HistoryResponseDto
import java.util.UUID

interface HistoryQueryUseCase {
    fun getHistoryFromWeek(query: GetHistoryQuery): List<HistoryResponseDto>

    fun getHistoryFromMonth(query: GetHistoryQuery): List<HistoryResponseDto>

    fun getHistoryFromToday(userId: UUID): HistoryResponseDto?
}
