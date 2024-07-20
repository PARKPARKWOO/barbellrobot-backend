package com.example.infrastructure.persistence.repository.member

import com.example.infrastructure.persistence.entity.member.MemberGoalEntity
import com.example.infrastructure.persistence.entity.member.QMemberGoalEntity.memberGoalEntity
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

interface MemberGoalRepository : JpaRepository<MemberGoalEntity, Long>, MemberGoalQueryRepository

interface MemberGoalQueryRepository {
    fun getMemberGoal(memberId: UUID, goalId: Long): MemberGoalEntity?
}

@Repository
class MemberGoalQueryRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : MemberGoalQueryRepository {
    override fun getMemberGoal(memberId: UUID, goalId: Long): MemberGoalEntity? {
        return jpaQueryFactory.selectFrom(memberGoalEntity)
            .where(
                eqMember(memberId)
                    .and(eqGoal(goalId)),
            )
            .fetchOne()
    }

    private fun eqMember(memberId: UUID): BooleanExpression = memberGoalEntity.memberEntity.id.eq(memberId)

    private fun eqGoal(goalId: Long): BooleanExpression = memberGoalEntity.exerciseGoalEntity.id.eq(goalId)
}
