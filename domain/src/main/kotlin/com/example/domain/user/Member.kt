package com.example.domain.user

import com.example.domain.constants.DomainConstants
import java.time.LocalDateTime
import java.util.UUID

data class Member(
    val id: UUID,
    var nickname: String,
    val email: String,
    var password: String,
    val role: Role,
    var gender: Gender,
    val provider: String?,
    var exerciseMonths: Int,
    var tall: Double,
    var weight: Double,
    // 골격근량
    var skeletalMuscleMass: Double?,
    var age: Int,
    val createdAt: LocalDateTime,
    val deletedAt: LocalDateTime?,
    var profile: String?,
) : User {
    override fun getClaims(): Map<String, Any> {
        val claims = mutableMapOf<String, Any>()
        claims[DomainConstants.USER_ID] = id
        claims[DomainConstants.USER_ROLE] = role
        return claims
    }

    fun updateNickname(nickname: String) {
        this.nickname = nickname
    }

    fun updateInfo(
        exerciseMonths: Int,
        tall: Double,
        weight: Double,
        skeletalMuscleMass: Double?,
        gender: Gender,
    ) {
        this.exerciseMonths = exerciseMonths
        this.tall = tall
        this.weight = weight
        this.skeletalMuscleMass = skeletalMuscleMass
        this.gender = gender
    }

    fun summary() = MemberSummary(
        id = id,
        nickname = nickname,
        profile = profile,
    )
}
