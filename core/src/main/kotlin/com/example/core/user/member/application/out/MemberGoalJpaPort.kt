package com.example.core.user.member.application.out

import com.example.core.user.member.application.command.AddGoalCommand
import com.example.core.user.member.application.command.DeleteMemberGoalCommand
import java.util.UUID

interface MemberGoalJpaPort {
    fun create(memberId: UUID)

    fun addGoal(command: AddGoalCommand)

    fun deleteGoal(command: DeleteMemberGoalCommand)
}
