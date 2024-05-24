package com.example.core.user.member.application.out

import com.example.core.user.member.application.command.AddGoalCommand
import com.example.core.user.member.application.command.DeleteMemberGoalCommand

interface MemberGoalJpaPort {
    fun addGoal(command: AddGoalCommand)

    fun deleteGoal(command: DeleteMemberGoalCommand)
}
