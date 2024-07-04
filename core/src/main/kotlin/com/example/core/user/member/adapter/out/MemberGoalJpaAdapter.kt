package com.example.core.user.member.adapter.out

import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.exercise.application.port.out.ExerciseGoalJpaPort
import com.example.core.user.member.adapter.out.persistence.entity.MemberEntity
import com.example.core.user.member.adapter.out.persistence.entity.MemberGoalEntity
import com.example.core.user.member.adapter.out.persistence.repository.MemberGoalRepository
import com.example.core.user.member.adapter.out.persistence.repository.MemberRepository
import com.example.core.user.member.application.command.AddGoalCommand
import com.example.core.user.member.application.command.DeleteMemberGoalCommand
import com.example.core.user.member.application.out.MemberGoalJpaPort
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

        val existingMemberGoals = memberGoalRepository.getMemberGoalByIds(
            memberId = command.memberId,
            goalIds = command.goalIds,
        )

        existingMemberGoals.filter { it.isDeleted }.forEach { it.revert() }

        val existingGoalIds = existingMemberGoals.map { it.exerciseGoalEntity.id }
        val newGoalIds = command.goalIds.filterNot { existingGoalIds.contains(it) }

        val newGoals = exerciseGoalJpaPort.getExerciseGoals(newGoalIds)?.map { goal ->
            MemberGoalEntity(
                memberEntity = member,
                exerciseGoalEntity = goal,
            )
        }.orEmpty()

        // Save new goals
        if (newGoals.isNotEmpty()) {
            memberGoalRepository.saveAll(newGoals)
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
