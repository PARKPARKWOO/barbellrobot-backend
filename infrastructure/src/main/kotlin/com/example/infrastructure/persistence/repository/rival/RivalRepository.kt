package com.example.infrastructure.persistence.repository.rival

import com.example.core.rival.dto.RivalSummaryDto
import com.example.core.rival.model.RivalStatus.PENDING
import com.example.infrastructure.persistence.entity.member.QMemberEntity.memberEntity
import com.example.infrastructure.persistence.entity.member.QMemberInfo.memberInfo
import com.example.infrastructure.persistence.entity.rival.QRivalCurrentSituationEntity.rivalCurrentSituationEntity
import com.example.infrastructure.persistence.entity.rival.QRivalEntity.rivalEntity
import com.example.infrastructure.persistence.entity.rival.RivalEntity
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface RivalRepository : JpaRepository<RivalEntity, UUID>, RivalQueryRepository {
    fun findByMemberId(memberId: UUID): RivalEntity?
}

interface RivalQueryRepository {
    fun findMyRivals(memberId: UUID): List<RivalSummaryDto>

    fun findPendingFromMe(memberId: UUID): List<RivalSummaryDto>
}

class RivalQueryRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : RivalQueryRepository {
    override fun findMyRivals(memberId: UUID): List<RivalSummaryDto> {
        return jpaQueryFactory.select(
            Projections.constructor(
                RivalSummaryDto::class.java,
                memberInfo.userId,
                memberInfo.nickname,
                memberEntity.profile,
                rivalCurrentSituationEntity.rivalStatus,
            ),
        ).from(rivalEntity)
            .leftJoin(rivalEntity.myRivals, rivalCurrentSituationEntity)
            .on(
                rivalCurrentSituationEntity.sender.eq(memberId)
                    .or(rivalCurrentSituationEntity.receiver.eq(memberId)),
            )
            .leftJoin(memberEntity)
            .on(memberEntity.id.eq(memberId))
            .leftJoin(memberInfo)
            .on(memberInfo.userId.eq(memberId))
            .where(rivalEntity.memberId.eq(memberId))
            .fetch()
    }

    override fun findPendingFromMe(memberId: UUID): List<RivalSummaryDto> {
        return jpaQueryFactory.select(
            Projections.constructor(
                RivalSummaryDto::class.java,
                rivalCurrentSituationEntity.sender,
                memberInfo.nickname,
                memberEntity.profile,
                rivalCurrentSituationEntity.rivalStatus,
            ),
        ).from(rivalCurrentSituationEntity)
            .leftJoin(memberInfo).on(memberInfo.userId.eq(rivalCurrentSituationEntity.sender))
            .leftJoin(memberEntity).on(memberEntity.id.eq(rivalCurrentSituationEntity.sender))
            .where(
                rivalCurrentSituationEntity.rivalStatus.eq(PENDING)
                    .and(rivalCurrentSituationEntity.receiver.eq(memberId)),
            )
            .fetch()
    }
}
