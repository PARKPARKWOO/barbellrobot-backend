package com.example.core.history.port.out.command

import com.example.core.history.model.Diet
import java.util.UUID

data class SaveDietHistoryCommand(
    val historyId: UUID,
    val type: Diet,
    val food: String,
)
