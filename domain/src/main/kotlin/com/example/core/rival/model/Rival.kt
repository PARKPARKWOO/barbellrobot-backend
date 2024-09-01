package com.example.core.rival.model

import java.util.UUID

data class Rival(
    val memberId: UUID,
    val myRivals: List<RivalCurrentSituation>,
)
