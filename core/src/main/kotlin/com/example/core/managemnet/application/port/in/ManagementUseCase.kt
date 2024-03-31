package com.example.core.managemnet.application.port.`in`

import com.example.core.managemnet.application.port.command.AddManagementMemberCommand
import com.example.domain.user.MemberSummary
import com.example.domain.user.Trainer
import java.util.UUID

interface ManagementUseCase {
    fun create(trainer: Trainer)

    fun addManagementMember(command: AddManagementMemberCommand)

    fun getMemberSummary(trainerId: UUID): List<MemberSummary>
}
