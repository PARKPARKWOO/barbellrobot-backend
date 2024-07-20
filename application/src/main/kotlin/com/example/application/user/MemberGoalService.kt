package com.example.application.user

import com.example.core.user.port.command.AddGoalCommand
import com.example.core.user.port.command.DeleteMemberGoalCommand
import com.example.core.user.port.`in`.MemberGoalUseCase
import com.example.core.user.port.out.MemberGoalJpaPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberGoalService(
    private val memberGoalJpaPort: MemberGoalJpaPort,
) : MemberGoalUseCase {
    @Transactional
    override fun addGoal(command: AddGoalCommand) {
        // TODO: 반드시 리팩토링 필요
        memberGoalJpaPort.addGoal(command)
    }

    @Transactional
    override fun deleteGoal(command: DeleteMemberGoalCommand) {
        memberGoalJpaPort.deleteGoal(command)
    }
}
