package com.example.core.history.port.`in`

import com.example.core.history.port.command.AddDietJpaCommand
import com.example.core.history.port.command.ExerciseTodayCommand
import java.util.UUID

interface GenerateHistoryUseCase {
    fun exerciseToday(command: ExerciseTodayCommand)

    fun attendanceToday(userId: UUID): UUID

    fun addDietCommand(command: AddDietJpaCommand)
}
