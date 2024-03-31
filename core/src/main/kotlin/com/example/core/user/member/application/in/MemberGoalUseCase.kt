package com.example.core.user.member.application.`in`

import com.example.core.user.member.application.command.AddGoalCommand
import java.util.UUID

interface MemberGoalUseCase {
    fun create(memberId: UUID)

    fun addGoal(command: AddGoalCommand)
}
