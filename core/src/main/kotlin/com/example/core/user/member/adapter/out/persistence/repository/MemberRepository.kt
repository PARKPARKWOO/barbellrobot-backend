package com.example.core.user.member.adapter.out.persistence.repository

import com.example.core.exercise.adapter.out.persistence.entity.QExerciseGoalEntity.exerciseGoalEntity
import com.example.core.sign.application.port.`in`.query.FindUserWithSocialQuery
import com.example.core.user.member.adapter.out.persistence.entity.MemberEntity
import com.example.core.user.member.adapter.out.persistence.entity.QMemberEntity
import com.example.core.user.member.adapter.out.persistence.entity.QMemberEntity.memberEntity
import com.example.core.user.member.adapter.out.persistence.entity.QMemberGoalEntity.memberGoalEntity
import com.example.core.user.member.adapter.out.persistence.entity.QMemberInfo.memberInfo
import com.example.core.user.member.dto.MemberAndGoalQueryDto
import com.example.core.user.member.dto.QMemberDetailQueryDto
import com.example.core.user.member.dto.QMemberInfoQueryDto
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

interface MemberRepository : JpaRepository<MemberEntity, UUID>, MemberQueryRepository {
    fun findByEmailAndPassword(email: String, password: String): MemberEntity?
}

interface MemberQueryRepository {
    fun findMemberAndGoal(memberId: UUID): MemberAndGoalQueryDto?

    fun findWithSocial(query: FindUserWithSocialQuery): MemberEntity?
}

@Repository
class MemberQueryRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : MemberQueryRepository {
    override fun findMemberAndGoal(memberId: UUID): MemberAndGoalQueryDto? {
        val memberDetails = jpaQueryFactory.select(
            QMemberDetailQueryDto(
                QMemberEntity.memberEntity.id,
                QMemberEntity.memberEntity.email,
                QMemberEntity.memberEntity.password,
                QMemberEntity.memberEntity.role,
                QMemberEntity.memberEntity.socialProvider.provider,
                QMemberEntity.memberEntity.createdAt,
                QMemberEntity.memberEntity.deletedAt,
                QMemberEntity.memberEntity.profile,
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
            .where(memberGoalEntity.memberEntity.id.eq(memberId))
            .fetch()

        return memberDetails?.let {
            MemberAndGoalQueryDto(
                goal = goals,
                exerciseMonths = it.memberInfoQueryDto!!.exerciseMonths!!,
                tall = it.memberInfoQueryDto.tall!!,
                weight = memberDetails.memberInfoQueryDto!!.weight!!,
                age = memberDetails.memberInfoQueryDto.age!!,
                gender = memberDetails.memberInfoQueryDto.gender!!,
            )
        }
    }

    override fun findWithSocial(query: FindUserWithSocialQuery): MemberEntity? {
        return jpaQueryFactory.selectFrom(memberEntity)
            .where(
                memberEntity.socialProvider.socialId.eq(query.id)
                    .and(memberEntity.socialProvider.provider.eq(query.provider)),
            )
            .fetchOne()
    }
}
