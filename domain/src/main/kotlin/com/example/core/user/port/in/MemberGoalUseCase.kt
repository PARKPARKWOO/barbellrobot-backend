package com.example.core.user.port.`in`

import com.example.core.user.port.command.AddGoalCommand
import com.example.core.user.port.command.DeleteMemberGoalCommand

interface MemberGoalUseCase {
    fun addGoal(command: AddGoalCommand)

    fun deleteGoal(command: DeleteMemberGoalCommand)
}
