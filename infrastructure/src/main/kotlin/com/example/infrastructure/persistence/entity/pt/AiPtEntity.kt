package com.example.infrastructure.persistence.entity.pt

import com.example.common.mapper.convert
import com.example.core.common.persistence.BaseEntity
import com.example.core.pt.model.AiPt
import com.example.core.user.member.adapter.out.persistence.entity.MemberEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType.LAZY
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

const val AI_PT_TABLE_NAME = "ai_pt"

typealias AiPtModel = AiPt

@Entity
@Table(name = AI_PT_TABLE_NAME)
class AiPtEntity(
    @ManyToOne(fetch = LAZY)
    val member: MemberEntity,
    @Column(name = "consulting", columnDefinition = "JSON")
    @JdbcTypeCode(SqlTypes.JSON)
    var consulting: Map<String, Any>,
) : BaseEntity() {
    fun toDomain(): AiPtModel = AiPtModel(
        consulting = consulting.convert(),
        createdAt = createdAt,
        member = member.toDomain(),
    )
}
