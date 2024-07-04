package com.example.core.rival.adapter.out.persistence.entity

import com.example.core.common.persistence.BaseEntity
import com.example.domain.rival.RivalStatus
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.Enumerated
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.util.UUID

// status 는 아닌듯
@Entity
class RivalStatusEntity(
    @Column(name = "sender")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    val sender: UUID,
    @Column(name = "receiver")
    val receiver: UUID,
    @Enumerated(STRING)
    val rivalStatus: RivalStatus,
) : BaseEntity()
