package com.example.`in`.api.rival.adapter.`in`.request

import com.example.core.rival.port.command.ProdRivalCommand
import java.util.UUID

data class ProdRivalRequest(
    val rivalId: UUID,
) {
    fun toCommand(userId: UUID): ProdRivalCommand = ProdRivalCommand(
        sender = userId,
        receiver = rivalId,
    )
}
