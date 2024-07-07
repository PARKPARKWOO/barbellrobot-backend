package com.example.core.managemnet.application.service

import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.managemnet.application.port.command.ApprovalCommand
import com.example.core.managemnet.application.port.command.CancelCommand
import com.example.core.managemnet.application.port.command.OfferCommand
import com.example.core.managemnet.application.port.command.RejectCommand
import com.example.core.managemnet.application.port.`in`.ManagementUseCase
import com.example.core.managemnet.application.port.out.ManagementJpaPort
import com.example.core.user.member.dto.MemberSummaryDto
import com.example.core.managemnet.model.Management
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class ManagementService(
    private val managementJpaPort: ManagementJpaPort,
) : ManagementUseCase {
    @Transactional
    override fun offer(command: OfferCommand) {
        managementJpaPort.findByMemberIdAndTrainerId(command)?.let {
            it.offer(command.endDate)
            managementJpaPort.update(it)
        } ?: managementJpaPort.create(command)
    }

    @Transactional
    override fun cancel(command: CancelCommand) {
        val management = managementJpaPort.getManagement(command.managementId)
        if (management.memberId == command.memberId) {
            management.cancel()
            managementJpaPort.update(management)
        } else {
            throw ServiceException(ErrorCode.NOT_GRANT_MANAGEMENT)
        }
    }

    @Transactional
    override fun reject(command: RejectCommand) {
        val management = managementJpaPort.getManagement(command.managementId)
        if (command.trainerId == management.trainerId) {
            management.reject()
            managementJpaPort.update(management)
        } else {
            throw ServiceException(ErrorCode.NOT_GRANT_MANAGEMENT)
        }
    }

    @Transactional
    override fun approval(command: ApprovalCommand) {
        val management = managementJpaPort.getManagement(command.managementId)
        if (command.trainerId == management.trainerId) {
            management.approval()
            managementJpaPort.update(management)
        } else {
            throw ServiceException(ErrorCode.NOT_GRANT_MANAGEMENT)
        }
    }

    @Transactional(readOnly = true)
    override fun getManagementFromMember(memberId: UUID): List<Management> =
        managementJpaPort.getManagementFromMember(memberId) ?: emptyList()

    @Transactional(readOnly = true)
    override fun getMemberSummary(trainerId: UUID): List<MemberSummaryDto> =
        managementJpaPort.getManagementMemberSummary(trainerId)
}
