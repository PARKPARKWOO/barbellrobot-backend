package com.example.core.managemnet.adapter.out.persistence.entity

import com.example.core.common.persistence.BaseEntity
import com.example.core.user.member.adapter.out.persistence.entity.MemberEntity
import com.example.core.user.trainer.adapter.out.persistence.entity.TrainerEntity
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

const val MANAGEMENT_TABLE_NAME = "management"

@Entity
@Table(name = MANAGEMENT_TABLE_NAME)
class ManagementEntity(
    @ManyToOne(fetch = FetchType.LAZY)
    val trainer: TrainerEntity,
    @OneToMany(fetch = FetchType.LAZY)
    val memberList: MutableList<MemberEntity> = mutableListOf(),
) : BaseEntity() {
    fun addManagementMember(member: MemberEntity) {
        this.memberList.add(member)
    }
}
