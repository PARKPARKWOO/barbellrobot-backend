package com.example.core.managemnet.port.out

import com.example.core.managemnet.port.command.OfferCommand
import com.example.core.user.dto.MemberSummaryDto
import com.example.core.managemnet.model.Management
import java.util.UUID

interface ManagementJpaPort {
    fun create(command: OfferCommand)

    fun findByMemberIdAndTrainerId(command: OfferCommand): Management?

    fun update(management: Management)

    fun getManagement(managementId: Long): Management

    fun getManagementMemberSummary(trainerId: UUID): List<MemberSummaryDto>

    fun getManagementFromMember(memberId: UUID): List<Management>?
}
