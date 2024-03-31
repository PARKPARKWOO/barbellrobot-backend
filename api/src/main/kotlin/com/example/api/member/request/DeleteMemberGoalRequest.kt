package com.example.api.member.request

import com.example.core.user.member.application.command.DeleteMemberGoalCommand
import java.util.UUID

data class DeleteMemberGoalRequest(
    val goal: Long,
) {
    fun toCommand(memberId: UUID) = DeleteMemberGoalCommand(
        memberId = memberId,
        goal = goal,
    )
}
