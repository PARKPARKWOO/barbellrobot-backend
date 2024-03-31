package com.example.core.user.member.adapter.out

import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.user.member.adapter.out.persistence.entity.MemberGoalEntity
import com.example.core.user.member.adapter.out.persistence.repository.MemberGoalRepository
import com.example.core.user.member.application.command.AddGoalCommand
import com.example.core.user.member.application.command.DeleteMemberGoalCommand
import com.example.core.user.member.application.out.MemberGoalJpaPort
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class MemberGoalJpaAdapter(
    private val memberGoalRepository: MemberGoalRepository,
) : MemberGoalJpaPort {
    override fun create(memberId: UUID) {
        val memberGoalEntity = MemberGoalEntity(
            memberId = memberId,
        )
        memberGoalRepository.save(memberGoalEntity)
    }

    override fun addGoal(command: AddGoalCommand) {
        getEntity(command.memberId).addGoal(command.goalIds)
    }

    override fun deleteGoal(command: DeleteMemberGoalCommand) {
        getEntity(command.memberId).deleteGoal(command.goal)
    }

    private fun getEntity(memberId: UUID): MemberGoalEntity {
        return memberGoalRepository.findById(memberId).orElseThrow {
            throw ServiceException(ErrorCode.MEMBER_GOAL_NOT_FOUND)
        }
    }
}
