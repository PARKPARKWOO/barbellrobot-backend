package com.example.core.history.application.service

import com.example.core.history.application.port.`in`.GenerateHistoryUseCase
import com.example.core.history.application.port.`in`.command.ExerciseTodayCommand
import com.example.core.history.application.port.out.UserHistoryJpaPort
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class HistoryService(
    private val userHistoryJpaPort: UserHistoryJpaPort,
): GenerateHistoryUseCase {
    override fun exerciseToday(command: ExerciseTodayCommand) {

    }

    override fun attendanceToday(userId: UUID) {
        userHistoryJpaPort
    }
}
