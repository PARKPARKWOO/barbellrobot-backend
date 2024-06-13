package com.example.core.history.application.port.`in`

import com.example.core.history.application.port.command.AddDietJpaCommand
import com.example.core.history.application.port.command.ExerciseTodayCommand
import java.util.UUID

interface GenerateHistoryUseCase {
    fun exerciseToday(command: ExerciseTodayCommand)

    fun attendanceToday(userId: UUID): UUID

    fun addDietCommand(command: AddDietJpaCommand)
}
