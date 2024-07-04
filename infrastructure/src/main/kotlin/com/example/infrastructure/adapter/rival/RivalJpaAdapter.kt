package com.example.infrastructure.adapter.rival

import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.rival.dto.RivalSummaryDto
import com.example.core.rival.model.RivalStatus
import com.example.core.rival.port.command.RivalEventCommand
import com.example.core.rival.port.out.RivalJpaPort
import com.example.infrastructure.persistence.entity.rival.RivalCurrentSituationEntity
import com.example.infrastructure.persistence.entity.rival.RivalEntity
import com.example.infrastructure.persistence.repository.rival.RivalCurrentSituationRepository
import com.example.infrastructure.persistence.repository.rival.RivalRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class RivalJpaAdapter(
    private val rivalCurrentSituationRepository: RivalCurrentSituationRepository,
    private val rivalRepository: RivalRepository,
) : RivalJpaPort {
    override fun save(memberId: UUID) {
        val rivalEntity = RivalEntity(
            memberId = memberId,
        )
        rivalRepository.save(rivalEntity)
    }

    override fun refuseFromRivalRequest(command: RivalEventCommand) {
        rivalCurrentSituationRepository.findRequestStatusFromRequest(
            senderId = command.sender,
            receiverId = command.receiver,
        )?.refuseRival() ?: throw ServiceException(ErrorCode.NOT_FOUND_RIVAL_REQUEST)
    }

    override fun requestRival(command: RivalEventCommand) {
        val rivalCurrentSituationEntity = RivalCurrentSituationEntity(
            sender = command.sender,
            receiver = command.receiver,
            rivalStatus = RivalStatus.PENDING,
        )
        val newEntity = rivalCurrentSituationRepository.save(rivalCurrentSituationEntity)
        val sender = rivalRepository.findByMemberId(command.sender)
        val receiver = rivalRepository.findByMemberId(command.receiver)
        if (sender != null && receiver != null) {
            sender.addRival(newEntity)
            receiver.addRival(newEntity)
        }
    }

    override fun acceptFromRivalRequest(command: RivalEventCommand) {
        rivalCurrentSituationRepository.findRequestStatusFromRequest(
            senderId = command.receiver,
            receiverId = command.receiver,
        )?.acceptRival() ?: throw ServiceException(ErrorCode.NOT_FOUND_RIVAL_REQUEST)
    }

    override fun findMyRivals(memberId: UUID): List<RivalSummaryDto> {
        return rivalRepository.findMyRivals(memberId)
    }

    override fun findPendingFromMe(memberId: UUID): List<RivalSummaryDto> {
        return rivalRepository.findPendingFromMe(memberId)
    }
}
