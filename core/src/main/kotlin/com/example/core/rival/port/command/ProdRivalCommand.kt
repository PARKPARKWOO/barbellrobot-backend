package com.example.core.rival.port.command

import com.example.core.rival.port.query.FindMyRivalByRivalIdQuery
import java.util.UUID

data class ProdRivalCommand(
    val sender: UUID,
    val receiver: UUID,
) {
    fun toQuery(): FindMyRivalByRivalIdQuery = FindMyRivalByRivalIdQuery(
        userId = sender,
        rivalId = receiver,
    )
}
