package com.example.api.member.request

import com.example.core.user.port.command.AddGoalCommand
import java.util.UUID

data class AddGoalRequest(
    val goalIds: List<Long>,
) {
    fun toCommand(memberId: UUID) = AddGoalCommand(
        memberId = memberId,
        goalIds = goalIds,
    )
}
