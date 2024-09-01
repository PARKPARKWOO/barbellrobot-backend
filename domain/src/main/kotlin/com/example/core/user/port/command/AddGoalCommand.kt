package com.example.core.user.port.command

import java.util.UUID

data class AddGoalCommand(
    val memberId: UUID,
    val goalIds: List<Long>,
) {
    constructor(memberId: UUID, vararg goalId: Long) : this(memberId, goalId.toList())
}
