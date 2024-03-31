package com.example.core.user.member.application.command

import java.util.UUID

data class AddGoalCommand(
    val memberId: UUID,
    val goalIds: List<Long>,
) {
    constructor(memberId: UUID, vararg goalId: Long) : this(memberId, goalId.toList())
}
