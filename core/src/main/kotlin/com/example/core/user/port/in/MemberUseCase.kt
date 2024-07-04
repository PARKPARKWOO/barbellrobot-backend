package com.example.core.user.port.`in`

import com.example.core.user.dto.MemberAndGoalQueryDto
import com.example.core.user.dto.MemberSummaryDto
import com.example.core.user.port.command.UpdateMemberInfoCommand
import java.util.UUID

interface MemberUseCase : UserUseCase {
    fun update(command: UpdateMemberInfoCommand)

    fun getMemberAndGoal(id: UUID): MemberAndGoalQueryDto

    fun getMemberByNickname(nickname: String): MemberSummaryDto?
}
