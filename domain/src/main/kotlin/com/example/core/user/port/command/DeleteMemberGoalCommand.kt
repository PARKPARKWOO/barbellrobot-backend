package com.example.core.user.port.command

import java.util.UUID

data class DeleteMemberGoalCommand(
    val memberId: UUID,
    val goal: Long,
)
