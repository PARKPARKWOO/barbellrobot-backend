package com.example.core.user.member.application.`in`

import com.example.core.user.member.application.command.AddGoalCommand
import com.example.core.user.member.application.command.DeleteMemberGoalCommand
import java.util.UUID

interface MemberGoalUseCase {
    fun create(memberId: UUID)

    fun addGoal(command: AddGoalCommand)

    fun deleteGoal(command: DeleteMemberGoalCommand)
}
