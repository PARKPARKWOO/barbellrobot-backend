package com.example.core.user.member.application.`in`

import com.example.core.user.application.port.`in`.UserUseCase
import com.example.core.user.member.application.command.UpdateMemberInfoCommand
import com.example.core.user.member.dto.MemberAndGoalQueryDto
import java.util.UUID

interface MemberUseCase : UserUseCase {
    fun update(command: UpdateMemberInfoCommand)

    fun getMemberAndGoal(id: UUID): MemberAndGoalQueryDto
}
