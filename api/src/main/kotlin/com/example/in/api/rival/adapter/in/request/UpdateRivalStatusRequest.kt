package com.example.`in`.api.rival.adapter.`in`.request

import com.example.core.rival.model.RivalStatus
import com.example.core.rival.port.command.RivalEventCommand
import java.util.UUID

data class UpdateRivalStatusRequest(
    val userId: UUID,
) {
    fun toAcceptCommand(receiver: UUID): RivalEventCommand = RivalEventCommand(
        receiver = receiver,
        sender = userId,
        rivalStatus = RivalStatus.ACTIVE,
    )

    fun toRefuseCommand(receiver: UUID): RivalEventCommand = RivalEventCommand(
        receiver = receiver,
        sender = userId,
        rivalStatus = RivalStatus.REFUSE,
    )

    fun toRequestCommand(sender: UUID): RivalEventCommand = RivalEventCommand(
        receiver = userId,
        sender = sender,
        rivalStatus = RivalStatus.PENDING,
    )
}
