package com.example.infrastructure.adapter.user

import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.exercise.port.out.ExerciseGoalJpaPort
import com.example.core.user.port.command.AddGoalCommand
import com.example.core.user.port.command.DeleteMemberGoalCommand
import com.example.core.user.port.out.MemberGoalJpaPort
import com.example.infrastructure.persistence.entity.exercise.ExerciseGoalEntity
import com.example.infrastructure.persistence.entity.member.MemberEntity
import com.example.infrastructure.persistence.entity.member.MemberGoalEntity
import com.example.infrastructure.persistence.repository.member.MemberGoalRepository
import com.example.infrastructure.persistence.repository.member.MemberRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class MemberGoalJpaAdapter(
    private val memberGoalRepository: MemberGoalRepository,
    private val memberRepository: MemberRepository,
    private val exerciseGoalJpaPort: ExerciseGoalJpaPort,
) : MemberGoalJpaPort {
    override fun addGoal(command: AddGoalCommand) {
        val member = getMember(command.memberId)
        val entity = exerciseGoalJpaPort.getExerciseGoals(command.goalIds)
        if (entity.isNotEmpty()) {
            val memberGoal = entity.map { goal ->
                MemberGoalEntity(
                    memberEntity = member,
                    exerciseGoalEntity = ExerciseGoalEntity.from(goal),
                )
            }
            memberGoalRepository.saveAll(memberGoal)
        }
    }

    override fun deleteGoal(command: DeleteMemberGoalCommand) {
        memberGoalRepository.getMemberGoal(
            memberId = command.memberId,
            goalId = command.goal,
        )?.let {
            memberGoalRepository.delete(it)
        }
    }

    private fun getMember(memberId: UUID): MemberEntity {
        return memberRepository.findById(memberId).orElseThrow {
            throw ServiceException(ErrorCode.MEMBER_NOT_FOUND)
        }
    }
}
