package com.example.core.history.port.`in`

import com.example.core.history.port.command.AddDietCommand

interface HistoryFacadeUseCase {
    suspend fun addDietToday(command: AddDietCommand)
}
