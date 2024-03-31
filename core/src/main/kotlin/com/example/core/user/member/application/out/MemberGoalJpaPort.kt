package com.example.core.user.member.application.out

import com.example.core.user.member.application.command.AddGoalCommand
import java.util.UUID

interface MemberGoalJpaPort {
    fun create(memberId: UUID)

    fun addGoal(command: AddGoalCommand)
}
