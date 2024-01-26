package com.example.core.member.adapter.out.persistence.entity

import com.example.core.common.persistence.BaseEntity
import com.example.domain.member.Member
import com.example.domain.member.Role
import jakarta.persistence.AttributeOverride
import jakarta.persistence.AttributeOverrides
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.SQLDelete

const val MEMBER_TABLE_NAME = "member"

@Entity
@Table(name = MEMBER_TABLE_NAME)
@SQLDelete(sql = "UPDATE $MEMBER_TABLE_NAME set deleted_at = now() WHERE id = ?")
@DynamicUpdate
class MemberEntity(
    @Column(name = "email")
    var email: String,
    @Column(name = "nickname")
    var nickname: String,
    @Column(name = "password")
    var password: String,
    @Column(name = "provider") // 소셜로그인 시
    var provider: String?,
    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "tall", column = Column(name = "tall")),
        AttributeOverride(name = "weight", column = Column(name = "weight")),
        AttributeOverride(name = "skeletalMuscleMass", column = Column(name = "skeletal_muscle_mass")),
        AttributeOverride(name = "age", column = Column(name = "age")),
        AttributeOverride(name = "exerciseMonths", column = Column(name = "exercise_months")),
    )
    var memberInfo: MemberInfo,
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    var role: Role,
    @Column(name = "gender")
    var gender: String,
) : BaseEntity() {
    fun toDomain(): Member {
        return Member(
            email = this.email,
            password = this.password,
            provider = this.provider,
            exerciseMonths = this.memberInfo.exerciseMonths,
            tall = this.memberInfo.tall,
            weight = this.memberInfo.weight,
            skeletalMuscleMass = this.memberInfo.skeletalMuscleMass,
            age = this.memberInfo.age,
            gender = this.gender,
            createdAt = this.createdAt,
            deletedAt = this.deletedAt,
            role = this.role,
            nickname = this.nickname,
        )
    }
}
