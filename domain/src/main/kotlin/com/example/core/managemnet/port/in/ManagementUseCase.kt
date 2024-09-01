package com.example.core.managemnet.port.`in`

import com.example.core.managemnet.port.command.ApprovalCommand
import com.example.core.managemnet.port.command.CancelCommand
import com.example.core.managemnet.port.command.OfferCommand
import com.example.core.managemnet.port.command.RejectCommand
import com.example.core.user.dto.MemberSummaryDto
import com.example.core.managemnet.model.Management
import java.util.UUID

interface ManagementUseCase {
    fun offer(command: OfferCommand)

    fun cancel(command: CancelCommand)

    fun reject(command: RejectCommand)

    fun approval(command: ApprovalCommand)

    fun getManagementFromMember(memberId: UUID): List<Management>

    fun getMemberSummary(trainerId: UUID): List<MemberSummaryDto>
}
