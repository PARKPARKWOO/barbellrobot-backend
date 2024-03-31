package com.example.core.managemnet.application.port.out

import com.example.core.managemnet.application.port.command.AddManagementMemberCommand
import com.example.domain.user.MemberSummary
import com.example.domain.user.Trainer
import java.util.UUID

interface ManagementJpaPort {
    fun create(trainer: Trainer)

    fun addManagementMember(command: AddManagementMemberCommand)

    fun getManagementMember(trainerId: UUID): List<MemberSummary>?
}
