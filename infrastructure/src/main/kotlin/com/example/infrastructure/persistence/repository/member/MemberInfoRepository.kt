package com.example.infrastructure.persistence.repository.member

import com.example.core.user.dto.MemberDetailQueryDto
import com.example.core.user.dto.MemberInfoQueryDto
import com.example.infrastructure.persistence.entity.member.MemberInfo
import com.example.infrastructure.persistence.entity.member.QMemberEntity.memberEntity
import com.example.infrastructure.persistence.entity.member.QMemberInfo.memberInfo
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

interface MemberInfoRepository :
    JpaRepository<MemberInfo, UUID>,
    MemberInfoQueryRepository

interface MemberInfoQueryRepository {
    fun findMemberDetailQuery(memberId: UUID): MemberDetailQueryDto?
}

@Repository
class MemberInfoQueryRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : MemberInfoQueryRepository {
    override fun findMemberDetailQuery(memberId: UUID): MemberDetailQueryDto? =
        jpaQueryFactory
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
}
