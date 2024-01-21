package com.example.core.member.adapter.out.persistence.entity

import com.example.core.common.persistence.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Entity
class MemberEntity(
    @Column(name = "email")
    var email: String,
    @Column(name = "password")
    var password: String,
    @Column(name = "provider") // 소셜로그인 시
    var provider: String,
    @Column(name = "exercise_months")
    var exerciseMonths: Int,
    @Column(name = "member_info")
    @Embedded
    var memberInfo: MemberInfo,
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    var role: Role,
) : BaseEntity()

enum class Role {
    ROLE_FREE,
    ROLE_ADMIN,
    ROLE_PRO,
}
