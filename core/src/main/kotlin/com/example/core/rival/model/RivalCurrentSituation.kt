package com.example.core.rival.model

import java.util.UUID

data class RivalCurrentSituation(
    val rivalMemberId: UUID,
    val rivalStatus: RivalStatus,
)
