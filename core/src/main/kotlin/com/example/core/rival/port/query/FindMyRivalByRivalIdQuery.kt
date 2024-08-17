package com.example.core.rival.port.query

import java.util.UUID

data class FindMyRivalByRivalIdQuery(
    val userId: UUID,
    val rivalId: UUID,
)
