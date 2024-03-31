package com.example.core.managemnet.adapter.out.persistence.repository

import com.example.core.managemnet.adapter.out.persistence.entity.ManagementEntity
import com.example.core.managemnet.adapter.out.persistence.entity.QManagementEntity.managementEntity
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

interface ManagementRepository : JpaRepository<ManagementEntity, UUID>, ManagementQueryRepository

interface ManagementQueryRepository {
    fun findFromTrainerId(trainerId: UUID): ManagementEntity?
}

@Repository
class ManagementQueryRepositoryImpl(
    private var jpaQueryFactory: JPAQueryFactory,
) : ManagementQueryRepository {
    override fun findFromTrainerId(trainerId: UUID): ManagementEntity? {
        return jpaQueryFactory.selectFrom(managementEntity)
            .where(managementEntity.trainerId.eq(trainerId))
            .fetchOne()
    }
}
