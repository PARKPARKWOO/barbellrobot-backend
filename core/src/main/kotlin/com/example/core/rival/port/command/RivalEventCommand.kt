package com.example.core.rival.port.command

import com.example.core.rival.model.RivalStatus
import java.util.UUID

data class RivalEventCommand(
    val sender: UUID,
    val receiver: UUID,
    val rivalStatus: RivalStatus,
)
