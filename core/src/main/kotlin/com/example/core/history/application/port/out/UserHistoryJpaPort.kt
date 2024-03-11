package com.example.core.history.application.port.out

import com.example.core.history.application.port.out.command.AddDietFoodCommand
import com.example.core.history.application.port.out.command.AddDietImageCommand
import com.example.core.history.application.port.out.query.FindUserHistoryQuery
import com.example.core.history.dto.HistoryResponseDto
import java.util.UUID

interface UserHistoryJpaPort {
    fun attendanceToday(command: AttendanceTodayCommand): UUID

    fun addImage(command: AddDietImageCommand)

    fun addFood(command: AddDietFoodCommand)

    fun queryUserHistory(query: FindUserHistoryQuery): List<HistoryResponseDto>?
}
