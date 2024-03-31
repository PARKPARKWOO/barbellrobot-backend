package com.example.core.managemnet.application.service

import com.example.core.managemnet.application.port.command.AddManagementMemberCommand
import com.example.core.managemnet.application.port.`in`.ManagementUseCase
import com.example.core.managemnet.application.port.out.ManagementJpaPort
import com.example.domain.user.MemberSummary
import com.example.domain.user.Trainer
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ManagementService(
    private val managementJpaPort: ManagementJpaPort,
) : ManagementUseCase {
    @Transactional
    override fun create(trainer: Trainer) {
        managementJpaPort.create(trainer)
    }

    @Transactional
    override fun addManagementMember(command: AddManagementMemberCommand) {
        managementJpaPort.addManagementMember(command)
    }

    @Transactional(readOnly = true)
    override fun getMemberSummary(trainerId: UUID): List<MemberSummary> {
        return managementJpaPort.getManagementMember(trainerId) ?: emptyList()
    }
}
