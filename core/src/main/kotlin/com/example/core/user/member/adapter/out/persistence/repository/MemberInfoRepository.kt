package com.example.core.user.member.adapter.out.persistence.repository

import com.example.core.user.member.adapter.out.persistence.entity.MemberInfo
import com.example.core.user.member.adapter.out.persistence.entity.QMemberEntity
import com.example.core.user.member.adapter.out.persistence.entity.QMemberEntity.memberEntity
import com.example.core.user.member.adapter.out.persistence.entity.QMemberInfo
import com.example.core.user.member.adapter.out.persistence.entity.QMemberInfo.memberInfo
import com.example.core.user.member.dto.MemberDetailQueryDto
import com.example.core.user.member.dto.QMemberDetailQueryDto
import com.example.core.user.member.dto.QMemberInfoQueryDto
import com.querydsl.jpa.impl.JPAQueryFactory
import org.hibernate.internal.util.NullnessHelper.coalesce
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

interface MemberInfoRepository : JpaRepository<MemberInfo, UUID>, MemberInfoQueryRepository

interface MemberInfoQueryRepository {
    fun findMemberDetailQuery(memberId: UUID): MemberDetailQueryDto?
}

@Repository
class MemberInfoQueryRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : MemberInfoQueryRepository {
    override fun findMemberDetailQuery(memberId: UUID): MemberDetailQueryDto? {
        return jpaQueryFactory.select(
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
    }

}
