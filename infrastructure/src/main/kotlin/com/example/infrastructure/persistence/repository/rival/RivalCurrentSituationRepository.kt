package com.example.infrastructure.persistence.repository.rival

import com.example.core.rival.model.RivalStatus.PENDING
import com.example.infrastructure.persistence.entity.rival.QRivalCurrentSituationEntity.rivalCurrentSituationEntity
import com.example.infrastructure.persistence.entity.rival.RivalCurrentSituationEntity
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface RivalCurrentSituationRepository :
    JpaRepository<RivalCurrentSituationEntity, UUID>,
    RivalCurrentSituationQueryRepository

interface RivalCurrentSituationQueryRepository {
    fun findRequestStatusFromRequest(senderId: UUID, receiverId: UUID): RivalCurrentSituationEntity?
}

class RivalCurrentSituationQueryRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : RivalCurrentSituationQueryRepository {
    override fun findRequestStatusFromRequest(senderId: UUID, receiverId: UUID): RivalCurrentSituationEntity? {
        return jpaQueryFactory.selectFrom(rivalCurrentSituationEntity)
            .where(
                rivalCurrentSituationEntity.rivalStatus.eq(PENDING)
                    .and(rivalCurrentSituationEntity.sender.eq(senderId))
                    .and(rivalCurrentSituationEntity.receiver.eq(receiverId)),
            ).fetchOne()
    }
}
