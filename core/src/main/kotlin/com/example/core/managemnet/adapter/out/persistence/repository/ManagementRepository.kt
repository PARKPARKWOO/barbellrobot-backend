package com.example.core.managemnet.adapter.out.persistence.repository

import com.example.core.managemnet.adapter.out.persistence.entity.ManagementEntity
import com.example.core.managemnet.adapter.out.persistence.entity.QManagementEntity.managementEntity
import com.example.core.user.member.adapter.out.persistence.entity.MemberEntity
import com.example.core.user.member.adapter.out.persistence.entity.QMemberEntity.memberEntity
import com.example.core.managemnet.model.ManagementStatus
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

interface ManagementRepository : JpaRepository<ManagementEntity, Long>, ManagementQueryRepository {
    fun findByMemberId(memberId: UUID): List<ManagementEntity>?

    fun findByMemberIdAndTrainerId(memberId: UUID, trainerId: UUID): ManagementEntity?
}

interface ManagementQueryRepository {
    fun findActiveFromTrainerId(trainerId: UUID): List<MemberEntity>?
}

@Repository
class ManagementQueryRepositoryImpl(
    private var jpaQueryFactory: JPAQueryFactory,
) : ManagementQueryRepository {
    override fun findActiveFromTrainerId(trainerId: UUID): List<MemberEntity>? {
        return jpaQueryFactory.select(memberEntity)
            .from(managementEntity)
            .leftJoin(memberEntity).on(memberEntity.id.eq(managementEntity.memberId))
            .where(
                managementEntity.trainerId.eq(trainerId)
                    .and(managementEntity.status.eq(ManagementStatus.ACTIVE)),
            )
            .fetch()
    }
}
