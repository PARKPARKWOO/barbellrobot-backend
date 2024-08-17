package com.example.infrastructure.adapter.rival

import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ErrorCode.FAILURE_REQUEST_RIVAL
import com.example.core.common.error.ServiceException
import com.example.core.rival.dto.RivalSummaryDto
import com.example.core.rival.model.RivalStatus
import com.example.core.rival.port.command.RivalEventCommand
import com.example.core.rival.port.out.RivalJpaPort
import com.example.core.rival.port.query.FindMyRivalByRivalIdQuery
import com.example.infrastructure.persistence.entity.rival.RivalCurrentSituationEntity
import com.example.infrastructure.persistence.entity.rival.RivalEntity
import com.example.infrastructure.persistence.repository.rival.RivalCurrentSituationRepository
import com.example.infrastructure.persistence.repository.rival.RivalRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class RivalJpaAdapter(
    private val rivalCurrentSituationRepository: RivalCurrentSituationRepository,
    private val rivalRepository: RivalRepository,
) : RivalJpaPort {
    override fun saveRival(memberId: UUID) {
        val rivalEntity = RivalEntity(
            memberId = memberId,
        )
        rivalRepository.save(rivalEntity)
    }

    override fun refuseFromRivalRequest(command: RivalEventCommand) {
        rivalCurrentSituationRepository.findRequestStatus(
            userId = command.sender,
            rivalId = command.receiver,
        )?.refuseRival() ?: throw ServiceException(ErrorCode.NOT_FOUND_RIVAL_REQUEST)

        rivalCurrentSituationRepository.findPendingStatus(
            userId = command.receiver,
            rivalId = command.sender,
        )?.refuseRival() ?: throw ServiceException(ErrorCode.NOT_FOUND_RIVAL_REQUEST)
    }

    override fun requestRival(command: RivalEventCommand) {
        val senderRivalEntity = rivalRepository.findByMemberId(command.sender)
        val receiverRivalEntity = rivalRepository.findByMemberId(command.receiver)
        if (senderRivalEntity == null || receiverRivalEntity == null) throw ServiceException(FAILURE_REQUEST_RIVAL)

        val senderEntity = RivalCurrentSituationEntity(
            rivalMemberId = command.receiver,
            rivalStatus = RivalStatus.REQUEST,
            rivalEntity = senderRivalEntity,
        )

        val receiverEntity = RivalCurrentSituationEntity(
            rivalMemberId = command.sender,
            rivalStatus = RivalStatus.PENDING,
            rivalEntity = receiverRivalEntity,
        )

        rivalCurrentSituationRepository.save(senderEntity)
        rivalCurrentSituationRepository.save(receiverEntity)
    }

    override fun acceptFromRivalRequest(command: RivalEventCommand) {
        rivalCurrentSituationRepository.findRequestStatus(
            userId = command.sender,
            rivalId = command.receiver,
        )?.acceptRival() ?: throw ServiceException(ErrorCode.NOT_FOUND_RIVAL_REQUEST)

        rivalCurrentSituationRepository.findPendingStatus(
            userId = command.receiver,
            rivalId = command.sender,
        )?.acceptRival() ?: throw ServiceException(ErrorCode.NOT_FOUND_RIVAL_REQUEST)
    }

    override fun findMyRivals(memberId: UUID): List<RivalSummaryDto> {
        return rivalRepository.findMyRivals(memberId)
    }

    override fun findPendingFromMe(memberId: UUID): List<RivalSummaryDto> {
        return rivalRepository.findPendingFromMe(memberId)
    }

    override fun findMyRivalByRivalId(query: FindMyRivalByRivalIdQuery): RivalSummaryDto? {
        return rivalRepository.findMyActiveRivalByRivalId(
            memberId = query.userId,
            rivalId = query.rivalId,
        )
    }
}
