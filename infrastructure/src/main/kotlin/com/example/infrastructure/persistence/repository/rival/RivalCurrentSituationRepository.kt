package com.example.infrastructure.persistence.repository.rival

import com.example.core.rival.model.RivalStatus.PENDING
import com.example.core.rival.model.RivalStatus.REQUEST
import com.example.infrastructure.persistence.entity.rival.QRivalCurrentSituationEntity.rivalCurrentSituationEntity
import com.example.infrastructure.persistence.entity.rival.QRivalEntity.rivalEntity
import com.example.infrastructure.persistence.entity.rival.RivalCurrentSituationEntity
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface RivalCurrentSituationRepository :
    JpaRepository<RivalCurrentSituationEntity, UUID>,
    RivalCurrentSituationQueryRepository

interface RivalCurrentSituationQueryRepository {
    fun findRequestStatus(userId: UUID, rivalId: UUID): RivalCurrentSituationEntity?

    fun findPendingStatus(userId: UUID, rivalId: UUID): RivalCurrentSituationEntity?
}

class RivalCurrentSituationQueryRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : RivalCurrentSituationQueryRepository {
    override fun findRequestStatus(userId: UUID, rivalId: UUID): RivalCurrentSituationEntity? {
        return jpaQueryFactory.selectFrom(rivalCurrentSituationEntity)
            .leftJoin(rivalEntity)
            .on(rivalEntity.memberId.eq(userId))
            .where(
                rivalCurrentSituationEntity.rivalStatus.eq(REQUEST)
                    .and(rivalCurrentSituationEntity.rivalMemberId.eq(rivalId))
                    .and(rivalEntity.memberId.eq(userId)),
            ).fetchOne()
    }

    override fun findPendingStatus(userId: UUID, rivalId: UUID): RivalCurrentSituationEntity? {
        return jpaQueryFactory.selectFrom(rivalCurrentSituationEntity)
            .leftJoin(rivalEntity)
            .on(rivalEntity.memberId.eq(userId))
            .where(
                rivalCurrentSituationEntity.rivalStatus.eq(PENDING)
                    .and(rivalCurrentSituationEntity.rivalMemberId.eq(rivalId))
                    .and(rivalEntity.memberId.eq(userId)),
            ).fetchOne()
    }
}
