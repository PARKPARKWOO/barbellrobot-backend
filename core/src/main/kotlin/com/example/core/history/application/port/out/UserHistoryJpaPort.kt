package com.example.core.history.application.port.out

import com.example.core.history.application.port.out.command.AddDietFoodCommand
import com.example.core.history.application.port.out.command.AddDietImageCommand
import com.example.core.history.application.port.out.command.AttendanceTodayCommand
import com.example.core.history.application.port.out.query.FindUserHistoryQuery
import com.example.core.history.dto.HistoryResponseDto
import com.example.core.history.model.UserHistory
import java.util.UUID

interface UserHistoryJpaPort {
    fun findUserHistory(query: AttendanceTodayCommand): UserHistory?

    fun attendanceToday(command: AttendanceTodayCommand): UUID

    fun addImage(command: AddDietImageCommand)

    fun addFood(command: AddDietFoodCommand)

    fun queryUserHistory(query: FindUserHistoryQuery): List<HistoryResponseDto>?
}
