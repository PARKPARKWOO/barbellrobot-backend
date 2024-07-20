package com.example.infrastructure.adapter.management

import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.managemnet.model.Management
import com.example.core.managemnet.model.ManagementStatus
import com.example.core.managemnet.port.command.OfferCommand
import com.example.core.managemnet.port.out.ManagementJpaPort
import com.example.core.user.dto.MemberSummaryDto
import com.example.infrastructure.persistence.entity.management.ManagementEntity
import com.example.infrastructure.persistence.repository.management.ManagementRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ManagementJpaAdapter(
    private val managementRepository: ManagementRepository,
) : ManagementJpaPort {
    override fun create(command: OfferCommand) {
        val managementEntity = ManagementEntity(
            trainerId = command.trainerId,
            memberId = command.memberId,
            status = ManagementStatus.OFFER,
            endDate = command.endDate,
        )
        managementRepository.save(managementEntity)
    }

    override fun findByMemberIdAndTrainerId(command: OfferCommand): Management? {
        return managementRepository.findByMemberIdAndTrainerId(command.memberId, command.trainerId)?.toDomain()
    }

    override fun update(management: Management) {
        getEntity(management.id).update(management)
    }

    override fun getManagement(managementId: Long): Management {
        return getEntity(managementId).toDomain()
    }

    override fun getManagementMemberSummary(trainerId: UUID): List<MemberSummaryDto> {
        TODO()
//        return managementRepository.findActiveFromTrainerId(trainerId)?.let {
//            it.map { dto ->
//                dto.toDomain(getMemberInfo(dto.id)).summary()
//            }
//        }
    }

    override fun getManagementFromMember(memberId: UUID): List<Management>? {
        return managementRepository.findByMemberId(memberId)?.map { it.toDomain() }
    }

    private fun getEntity(managementId: Long): ManagementEntity {
        return managementRepository.findById(managementId).orElseThrow {
            throw ServiceException(ErrorCode.NOT_FOUND_MANAGEMENT)
        }
    }
}
