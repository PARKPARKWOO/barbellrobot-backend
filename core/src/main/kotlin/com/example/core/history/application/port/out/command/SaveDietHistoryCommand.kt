package com.example.core.history.application.port.out.command

import com.example.domain.history.Diet
import java.util.UUID

data class SaveDietHistoryCommand(
    val historyId: UUID,
    val type: Diet,
    val food: String,
)
