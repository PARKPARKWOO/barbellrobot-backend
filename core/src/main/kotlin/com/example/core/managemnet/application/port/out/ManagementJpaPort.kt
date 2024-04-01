package com.example.core.managemnet.application.port.out

import com.example.core.managemnet.application.port.command.OfferCommand
import com.example.domain.management.Management
import com.example.domain.user.MemberSummary
import java.util.UUID

interface ManagementJpaPort {
    fun create(command: OfferCommand)

    fun findByMemberIdAndTrainerId(command: OfferCommand): Management?

    fun update(management: Management)

    fun getManagement(managementId: Long): Management

    fun getManagementMemberSummary(trainerId: UUID): List<MemberSummary>?

    fun getManagementFromMember(memberId: UUID): List<Management>?
}
