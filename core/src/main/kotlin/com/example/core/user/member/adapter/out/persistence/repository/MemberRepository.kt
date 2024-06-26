package com.example.core.user.member.adapter.out.persistence.repository

import com.example.core.exercise.adapter.out.persistence.entity.QExerciseGoalEntity.exerciseGoalEntity
import com.example.core.sign.application.port.`in`.query.FindUserWithSocialQuery
import com.example.core.user.member.adapter.out.persistence.entity.MemberEntity
import com.example.core.user.member.adapter.out.persistence.entity.QMemberEntity.memberEntity
import com.example.core.user.member.adapter.out.persistence.entity.QMemberGoalEntity.memberGoalEntity
import com.example.core.user.member.adapter.out.persistence.entity.QMemberInfo.memberInfo
import com.example.core.user.member.dto.MemberAndGoalQueryDto
import com.example.core.user.member.dto.QMemberDetailQueryDto
import com.example.core.user.member.dto.QMemberInfoQueryDto
import com.example.domain.user.Provider
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
}

@Repository
class MemberQueryRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : MemberQueryRepository {
    override fun findMemberDetailAndGoal(memberId: UUID): MemberAndGoalQueryDto? {
        val memberDetails = jpaQueryFactory
            .select(
                QMemberDetailQueryDto(
                    memberEntity.id,
                    memberEntity.email,
                    memberEntity.password,
                    memberEntity.role,
                    memberEntity.socialProvider.provider,
                    memberEntity.createdAt,
                    memberEntity.deletedAt,
                    memberEntity.profile,
                    QMemberInfoQueryDto(
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

    private fun eqSocialId(id: String): BooleanExpression =
        memberEntity.socialProvider.socialId.eq(id)

    private fun eqSocialProvider(provider: Provider): BooleanExpression =
        memberEntity.socialProvider.provider.eq(provider)
}
