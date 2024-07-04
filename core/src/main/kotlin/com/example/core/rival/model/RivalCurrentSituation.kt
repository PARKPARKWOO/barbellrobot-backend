package com.example.core.rival.model

import java.util.UUID

data class RivalCurrentSituation(
    val sender: UUID,
    val receiver: UUID,
    val rivalStatus: RivalStatus,
)
