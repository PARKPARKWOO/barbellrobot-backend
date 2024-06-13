package com.example.core.history.application.port.`in`

import com.example.core.history.application.port.command.AddDietCommand

interface HistoryFacadeUseCase {
    suspend fun addDietToday(command: AddDietCommand)
}
