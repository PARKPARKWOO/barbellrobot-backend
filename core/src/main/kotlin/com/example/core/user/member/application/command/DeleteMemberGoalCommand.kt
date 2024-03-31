package com.example.core.user.member.application.command

import java.util.UUID

data class DeleteMemberGoalCommand(
    val memberId: UUID,
    val goal: Long,
)
