package com.example.core.user.member.application.service

import com.example.core.user.member.application.command.AddGoalCommand
import com.example.core.user.member.application.`in`.MemberGoalUseCase
import com.example.core.user.member.application.out.MemberGoalJpaPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class MemberGoalService(
    private val memberGoalJpaPort: MemberGoalJpaPort,
): MemberGoalUseCase {
    @Transactional
    override fun create(memberId: UUID) {
        memberGoalJpaPort.create(memberId)
    }

    @Transactional
    override fun addGoal(command: AddGoalCommand) {
        memberGoalJpaPort.addGoal(command)
    }
}
