package com.example.infrastructure.persistence.repository.pt

import com.example.infrastructure.persistence.entity.member.QMemberEntity.memberEntity
import com.example.infrastructure.persistence.entity.pt.AiPtEntity
import com.example.infrastructure.persistence.entity.pt.QAiPtEntity.aiPtEntity
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.UUID

interface AiPtRepository : JpaRepository<AiPtEntity, UUID>, AiPtQueryRepository

interface AiPtQueryRepository {
    fun findByBetweenDateTime(memberId: UUID, startDateTime: LocalDate, endTime: LocalDate): AiPtEntity?
}

@Repository
class AiPtQueryRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : AiPtQueryRepository {
    override fun findByBetweenDateTime(memberId: UUID, startDateTime: LocalDate, endTime: LocalDate): AiPtEntity? {
        return jpaQueryFactory.selectFrom(aiPtEntity)
            .leftJoin(aiPtEntity.member, memberEntity).fetchJoin()
            .where(
                betweenDateTime(startDateTime, endTime)
                    .and(aiPtEntity.member.id.eq(memberId)),
            )
            .fetchOne()
    }

    private fun betweenDateTime(startDateTime: LocalDate, endTime: LocalDate): BooleanExpression {
        // LocalDateTime 형식으로 변환
        val startOfWeek = startDateTime.atStartOfDay()
        val endOfWeek = endTime.atTime(23, 59, 59)

        return aiPtEntity.createdAt.between(startOfWeek, endOfWeek)
    }
}
