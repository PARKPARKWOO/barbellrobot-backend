package com.example.core.history.application.port.`in`

import com.example.core.history.application.port.`in`.command.ExerciseTodayCommand
import java.util.UUID

interface GenerateHistoryUseCase {
    fun exerciseToday(command: ExerciseTodayCommand)

    fun attendanceToday(userId: UUID)
}
