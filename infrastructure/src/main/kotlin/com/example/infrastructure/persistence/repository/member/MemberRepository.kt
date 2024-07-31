package com.example.infrastructure.persistence.repository.member

import com.example.core.sign.port.`in`.query.FindUserWithSocialQuery
import com.example.core.user.dto.MemberAndGoalQueryDto
import com.example.core.user.dto.MemberDetailQueryDto
import com.example.core.user.dto.MemberInfoQueryDto
import com.example.core.user.dto.MemberSummaryDto
import com.example.core.user.model.Provider
import com.example.infrastructure.persistence.entity.exercise.QExerciseGoalEntity.exerciseGoalEntity
import com.example.infrastructure.persistence.entity.member.MemberEntity
import com.example.infrastructure.persistence.entity.member.QMemberEntity.memberEntity
import com.example.infrastructure.persistence.entity.member.QMemberGoalEntity.memberGoalEntity
import com.example.infrastructure.persistence.entity.member.QMemberInfo.memberInfo
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

interface MemberRepository : JpaRepository<MemberEntity, UUID>, MemberQueryRepository {
    fun findByEmailAndPassword(email: String, password: String): MemberEntity?
}

interface MemberQueryRepository {
    fun findMemberDetailAndGoal(memberId: UUID): MemberAndGoalQueryDto?

    fun findWithSocial(query: FindUserWithSocialQuery): MemberEntity?

    fun getMemberSummaryDto(nickname: String): MemberSummaryDto?
}

@Repository
class MemberQueryRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : MemberQueryRepository {
    override fun findMemberDetailAndGoal(memberId: UUID): MemberAndGoalQueryDto? {
        val memberDetails = jpaQueryFactory
            .select(
                Projections.constructor(
                    MemberDetailQueryDto::class.java,
                    memberEntity.id,
                    memberEntity.email,
                    memberEntity.password,
                    memberEntity.role,
                    memberEntity.socialProvider.provider,
                    memberEntity.createdAt,
                    memberEntity.deletedAt,
                    memberEntity.profile,
                    Projections.constructor(
                        MemberInfoQueryDto::class.java,
                        memberInfo.gender,
                        memberInfo.nickname,
                        memberInfo.exerciseMonths,
                        memberInfo.tall,
                        memberInfo.weight,
                        memberInfo.skeletalMuscleMass,
                        memberInfo.age,
                    ),
                ),
            )
            .from(memberEntity)
            .leftJoin(memberInfo).on(memberEntity.id.eq(memberInfo.userId))
            .where(memberEntity.id.eq(memberId))
            .fetchOne()

        // Fetch goals
        val goals = jpaQueryFactory
            .select(exerciseGoalEntity.goal)
            .from(memberGoalEntity)
            .leftJoin(memberGoalEntity.exerciseGoalEntity, exerciseGoalEntity)
            .where(
                memberGoalEntity.memberEntity.id.eq(memberId)
                    .and(memberGoalEntity.isDeleted.isFalse),
            )
            .fetch()

        return memberDetails?.let {
            MemberAndGoalQueryDto(
                memberGoal = goals,
                memberDetailQueryDto = it,
            )
        }
    }

    override fun findWithSocial(query: FindUserWithSocialQuery): MemberEntity? {
        return jpaQueryFactory
            .selectFrom(memberEntity)
            .where(
                eqSocialId(query.id)
                    .and(eqSocialProvider(query.provider)),
            ).fetchOne()
    }

    override fun getMemberSummaryDto(nickname: String): MemberSummaryDto? {
        return jpaQueryFactory
            .select(
                Projections.constructor(
                    MemberSummaryDto::class.java,
                    memberEntity.id,
                    memberInfo.nickname,
                    memberEntity.profile,
                ),
            )
            .from(memberEntity)
            .leftJoin(memberInfo).on(memberInfo.userId.eq(memberEntity.id))
            .where(memberInfo.nickname.eq(nickname))
            .fetchOne()
    }

    private fun eqSocialId(id: String): BooleanExpression =
        memberEntity.socialProvider.socialId.eq(id)

    private fun eqSocialProvider(provider: Provider): BooleanExpression =
        memberEntity.socialProvider.provider.eq(provider)
}
