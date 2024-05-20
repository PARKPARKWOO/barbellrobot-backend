package com.example.core.pt.adapter.out.persistence.entity

import com.example.core.common.persistence.BaseEntity
import com.example.core.user.member.adapter.out.persistence.entity.MemberEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType.LAZY
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

const val AI_PT_TABLE_NAME = "ai_pt"

typealias AiPtModel = com.example.domain.pt.AiPt

@Entity
@Table(name = AI_PT_TABLE_NAME)
class AiPtEntity(
    @ManyToOne(fetch = LAZY)
    val member: MemberEntity,
    @Column(name = "content", columnDefinition = "TEXT")
    val content: String,
) : BaseEntity() {
    fun toDomain(): AiPtModel = AiPtModel(
        content = content,
        createdAt = createdAt,
        member = member.toDomain(),
    )
}
