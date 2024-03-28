package com.example.core.history.application.port.`in`

import com.example.core.history.application.port.`in`.command.AddDietCommand

interface HistoryFacadeUseCase {
    suspend fun addDietToday(command: AddDietCommand)
}
