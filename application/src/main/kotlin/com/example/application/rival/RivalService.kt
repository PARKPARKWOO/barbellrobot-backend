package com.example.application.rival

import com.example.core.notification.model.SseEvent
import com.example.core.notification.model.SseEventType.RIVAL_ACCEPT
import com.example.core.notification.model.SseEventType.RIVAL_REQUEST
import com.example.core.rival.dto.RivalSummaryDto
import com.example.core.rival.model.RivalStatus
import com.example.core.rival.port.command.RivalEventCommand
import com.example.core.rival.port.`in`.RivalUseCase
import com.example.core.rival.port.out.RivalJpaPort
import com.example.core.rival.service.RivalRequestValidation
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class RivalService(
    private val rivalJpaPort: RivalJpaPort,
    private val applicationEventPublisher: ApplicationEventPublisher,
) : RivalUseCase {
    private val rivalRequestValidation = RivalRequestValidation()

    @Transactional
    override fun createRival(memberId: UUID) {
        rivalJpaPort.saveRival(memberId)
    }

    @Transactional(readOnly = true)
    override fun getMyRivals(memberId: UUID): List<RivalSummaryDto> = rivalJpaPort.findMyRivals(memberId)

    @Transactional(readOnly = true)
    override fun getPendingRivalList(memberId: UUID): List<RivalSummaryDto> = rivalJpaPort.findPendingFromMe(memberId)

    @Transactional
    override fun updateRivalStatus(command: RivalEventCommand) {
        when (command.rivalStatus) {
            RivalStatus.ACTIVE -> {
                rivalJpaPort.acceptFromRivalRequest(command)
                applicationEventPublisher.publishEvent(
                    SseEvent(
                        sender = command.sender,
                        receiver = command.receiver,
                        type = RIVAL_ACCEPT,
                        message = "",
                    ),
                )
            }

            RivalStatus.REFUSE -> rivalJpaPort.refuseFromRivalRequest(command)
            RivalStatus.PENDING -> {
                rivalRequestValidation.isRequestToSelf(
                    senderId = command.sender,
                    receiverId = command.receiver,
                )
                rivalJpaPort.requestRival(command)
                applicationEventPublisher.publishEvent(
                    SseEvent(
                        sender = command.sender,
                        receiver = command.receiver,
                        type = RIVAL_REQUEST,
                        message = "",
                    ),
                )
            }

            RivalStatus.REQUEST -> {
                // 내가 신청한 라이벌 목록 조회
            }
        }
    }
}
