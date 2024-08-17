package com.example.infrastructure.persistence.repository.rival

import com.example.core.rival.dto.RivalSummaryDto
import com.example.core.rival.model.RivalStatus.ACTIVE
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

    fun findMyActiveRivalByRivalId(memberId: UUID, rivalId: UUID): RivalSummaryDto?
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
        ).from(rivalCurrentSituationEntity)
            .leftJoin(rivalCurrentSituationEntity.rivalEntity, rivalEntity)
            .leftJoin(memberEntity)
            .on(
                memberEntity.id.eq(rivalCurrentSituationEntity.rivalMemberId),
            )
            .leftJoin(memberInfo)
            .on(memberInfo.userId.eq(memberEntity.id))
            .where(rivalEntity.memberId.eq(memberId).and(rivalCurrentSituationEntity.rivalStatus.eq(ACTIVE)))
            .fetch()
    }

    override fun findPendingFromMe(memberId: UUID): List<RivalSummaryDto> {
        return jpaQueryFactory.select(
            Projections.constructor(
                RivalSummaryDto::class.java,
                rivalCurrentSituationEntity.rivalMemberId,
                memberInfo.nickname,
                memberEntity.profile,
                rivalCurrentSituationEntity.rivalStatus,
            ),
        ).from(rivalCurrentSituationEntity)
            .leftJoin(memberInfo).on(memberInfo.userId.eq(rivalCurrentSituationEntity.rivalMemberId))
            .leftJoin(memberEntity).on(memberEntity.id.eq(rivalCurrentSituationEntity.rivalMemberId))
            .join(rivalCurrentSituationEntity.rivalEntity, rivalEntity)
            .where(
                rivalEntity.memberId.eq(memberId)
                    .and(rivalCurrentSituationEntity.rivalStatus.eq(PENDING)),
            )
            .fetch()
    }

    override fun findMyActiveRivalByRivalId(memberId: UUID, rivalId: UUID): RivalSummaryDto? {
        return jpaQueryFactory.select(
            Projections.constructor(
                RivalSummaryDto::class.java,
                memberInfo.userId,
                memberInfo.nickname,
                memberEntity.profile,
                rivalCurrentSituationEntity.rivalStatus,
            ),
        ).from(rivalCurrentSituationEntity)
            .leftJoin(rivalCurrentSituationEntity.rivalEntity, rivalEntity)
            .leftJoin(memberEntity)
            .on(
                memberEntity.id.eq(rivalCurrentSituationEntity.rivalMemberId),
            )
            .leftJoin(memberInfo)
            .on(memberInfo.userId.eq(memberEntity.id))
            .where(
                rivalEntity.memberId.eq(memberId)
                    .and(rivalCurrentSituationEntity.rivalStatus.eq(ACTIVE))
                    .and(rivalCurrentSituationEntity.rivalMemberId.eq(rivalId)),
            )
            .fetchOne()
    }
}
