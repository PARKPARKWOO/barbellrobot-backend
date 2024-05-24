package com.example.core.user.member.application.service

import com.example.core.user.member.application.command.AddGoalCommand
import com.example.core.user.member.application.command.DeleteMemberGoalCommand
import com.example.core.user.member.application.`in`.MemberGoalUseCase
import com.example.core.user.member.application.out.MemberGoalJpaPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberGoalService(
    private val memberGoalJpaPort: MemberGoalJpaPort,
) : MemberGoalUseCase {
    @Transactional
    override fun addGoal(command: AddGoalCommand) {
        memberGoalJpaPort.addGoal(command)
    }

    @Transactional
    override fun deleteGoal(command: DeleteMemberGoalCommand) {
        memberGoalJpaPort.deleteGoal(command)
    }
}
