package com.example.core.pt.adapter.out

import com.example.common.date.DateTimeConvert
import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.pt.adapter.out.persistence.entity.AiPtEntity
import com.example.core.pt.adapter.out.persistence.entity.AiPtModel
import com.example.core.pt.adapter.out.persistence.repository.AiPtRepository
import com.example.core.pt.application.command.SavePtCommand
import com.example.core.pt.application.port.out.PtJpaPort
import com.example.core.user.member.adapter.out.persistence.entity.MemberEntity
import com.example.core.user.member.adapter.out.persistence.repository.MemberRepository
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.util.UUID

@Component
class PtJpaAdapter(
    private val aiPtRepository: AiPtRepository,
    private val memberRepository: MemberRepository,
) : PtJpaPort {
    override fun save(command: SavePtCommand) {
        val aiPtEntity = AiPtEntity(
            member = getMember(command.memberId),
            content = command.content,
        )
        aiPtRepository.save(aiPtEntity)
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
