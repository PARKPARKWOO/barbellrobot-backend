package com.example.infrastructure.persistence.entity.rival

import com.example.infrastructure.common.persistence.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.util.UUID

const val RIVAL_ENTITY_TABLE_NAME = "rival"

@Entity
@Table(name = RIVAL_ENTITY_TABLE_NAME)
class RivalEntity(
    @Column(name = "member_id")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    val memberId: UUID,
) : BaseEntity()
