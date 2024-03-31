package com.example.core.managemnet.adapter.out.persistence.entity

import com.example.core.user.member.adapter.out.persistence.entity.MemberEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import java.util.UUID

const val MANAGEMENT_TABLE_NAME = "management"

@Entity
@Table(name = MANAGEMENT_TABLE_NAME)
class ManagementEntity(
    @Id
    @Column(name = "id")
    val trainerId: UUID,
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "managementEntity")
    val memberList: MutableList<MemberEntity> = mutableListOf(),
) {
    @Column(name = "created_at")
    @CreatedDate
    @NotNull
    lateinit var createdAt: LocalDateTime

    @Column(name = "updated_at")
    @LastModifiedDate
    @NotNull
    lateinit var updatedAt: LocalDateTime

    @Column(name = "deleted_at", nullable = true)
    var deletedAt: LocalDateTime? = null
    fun addManagementMember(member: MemberEntity) {
        this.memberList.add(member)
    }
}
