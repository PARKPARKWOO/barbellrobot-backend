package com.example.core.history.application.port.`in`.command

import java.util.UUID

data class ExerciseTodayCommand(
    val userHistoryId: UUID,
    // TODO 운동 세트, 무게, 종류
)
