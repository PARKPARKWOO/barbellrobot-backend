package com.example.core.user.port.out

import com.example.core.user.port.command.AddGoalCommand
import com.example.core.user.port.command.DeleteMemberGoalCommand

interface MemberGoalJpaPort {
    fun addGoal(command: AddGoalCommand)

    fun deleteGoal(command: DeleteMemberGoalCommand)
}
