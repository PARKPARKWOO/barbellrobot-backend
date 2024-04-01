package com.example.core.managemnet.application.port.`in`

import com.example.core.managemnet.application.port.command.ApprovalCommand
import com.example.core.managemnet.application.port.command.CancelCommand
import com.example.core.managemnet.application.port.command.OfferCommand
import com.example.core.managemnet.application.port.command.RejectCommand
import com.example.domain.management.Management
import com.example.domain.user.MemberSummary
import java.util.UUID

interface ManagementUseCase {
    fun offer(command: OfferCommand)

    fun cancel(command: CancelCommand)

    fun reject(command: RejectCommand)

    fun approval(command: ApprovalCommand)

    fun getManagementFromMember(memberId: UUID): List<Management>

    fun getMemberSummary(trainerId: UUID): List<MemberSummary>
}
