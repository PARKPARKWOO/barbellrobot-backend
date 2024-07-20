package com.example.infrastructure.adapter.pt

import com.example.application.common.mapper.serializeToMap
import com.example.application.common.util.DateTimeConvert
import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.pt.command.SavePtCommand
import com.example.core.pt.port.out.PtJpaPort
import com.example.infrastructure.persistence.entity.member.MemberEntity
import com.example.infrastructure.persistence.entity.pt.AiPtEntity
import com.example.infrastructure.persistence.entity.pt.AiPtModel
import com.example.infrastructure.persistence.repository.member.MemberRepository
import com.example.infrastructure.persistence.repository.pt.AiPtRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.UUID

@Component
class PtJpaAdapter(
    private val aiPtRepository: AiPtRepository,
    private val memberRepository: MemberRepository,
) : PtJpaPort {
    @Transactional
    override fun save(command: SavePtCommand): AiPtModel {
        val aiPtEntity = AiPtEntity(
            member = getMember(command.memberId),
            consulting = command.consulting.serializeToMap(),
        )
        return aiPtRepository.save(aiPtEntity).toDomain()
    }

    override fun findByThisWeek(memberId: UUID): AiPtModel? {
        val today = LocalDate.now()
        val startDate = DateTimeConvert.getStartOfWeek(today)
        val endDate = DateTimeConvert.getEndOfWeek(today)
        return aiPtRepository.findByBetweenDateTime(memberId, startDate, endDate)?.toDomain()
    }

    private fun getMember(memberId: UUID): MemberEntity {
        return memberRepository.findById(memberId).orElseThrow {
            throw ServiceException(ErrorCode.MEMBER_NOT_FOUND)
        }
    }
}
