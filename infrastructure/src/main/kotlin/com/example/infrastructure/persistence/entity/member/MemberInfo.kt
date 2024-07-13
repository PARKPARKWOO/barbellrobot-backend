package com.example.infrastructure.persistence.entity.member

import com.example.core.user.model.Gender
import com.example.core.user.model.MemberInfo
import com.example.core.user.model.interfaces.UserHealthDetail
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes.VARCHAR
import java.util.UUID

@Entity
@Table(name = "member_info")
class MemberInfo(
    @Id
    @Column(name = "member_id", columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(VARCHAR)
    override val userId: UUID,
    @Column(name = "nickname")
    var nickname: String,
    @Column(name = "tall")
    var tall: Double,
    @Column(name = "weight")
    var weight: Double,
    // 골격근량
    @Column(name = "skeletal_muscle_mass", nullable = true)
    var skeletalMuscleMass: Double?,
    @Enumerated(STRING)
    @Column(name = "gender")
    var gender: Gender,
    @Column(name = "age")
    var age: Int,
    @Column(name = "exercise_months")
    var exerciseMonths: Int,
) : UserHealthDetail {
    fun toDomain(): MemberInfo = MemberInfo(
        memberId = userId,
        gender = gender,
        exerciseMonths = exerciseMonths,
        tall = tall,
        age = age,
        weight = weight,
        skeletalMuscleMass = skeletalMuscleMass,
        nickname = nickname,
    )

    fun updateNickname(nickname: String) {
        this.nickname = nickname
    }

    fun update(memberInfo: MemberInfo) {
        this.tall = memberInfo.tall
        this.weight = memberInfo.weight
        this.age = memberInfo.age
        this.skeletalMuscleMass = memberInfo.skeletalMuscleMass
        this.exerciseMonths = memberInfo.exerciseMonths
        this.gender = memberInfo.gender
    }

    override fun toModel(): UserHealthDetail = this
}
