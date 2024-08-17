package com.example.`in`.api.member.request

import com.example.core.user.port.command.DeleteMemberGoalCommand
import java.util.UUID

data class DeleteMemberGoalRequest(
    val goal: Long,
) {
    fun toCommand(memberId: UUID) = DeleteMemberGoalCommand(
        memberId = memberId,
        goal = goal,
    )
}
