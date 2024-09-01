package com.example.core.user.model

import com.example.core.common.constants.AuthConstants
import com.example.core.user.model.interfaces.User
import com.example.core.user.model.interfaces.UserHealthDetail
import java.time.LocalDateTime
import java.util.UUID

data class Member(
    val id: UUID,
    val email: String?,
    var password: String?,
    val role: Role,
    val socialProvider: SocialProvider?,
    val createdAt: LocalDateTime,
    val deletedAt: LocalDateTime?,
    var profile: String?,
) : User {
    override fun getClaims(): Map<String, Any> {
        val claims = mutableMapOf<String, Any>()
        claims[AuthConstants.USER_ID] = id
        claims[AuthConstants.USER_ROLE] = role
        return claims
    }
}

data class MemberInfo(
    override val userId: UUID,
    var gender: Gender,
    var nickname: String,
    var exerciseMonths: Int,
    var tall: Double,
    var weight: Double,
    // 골격근량
    var skeletalMuscleMass: Double?,
    var age: Int,
) : UserHealthDetail {
    fun updateInfo(
        exerciseMonths: Int,
        tall: Double,
        weight: Double,
        skeletalMuscleMass: Double?,
        gender: Gender,
        age: Int,
    ) {
        this.exerciseMonths = exerciseMonths
        this.tall = tall
        this.weight = weight
        this.skeletalMuscleMass = skeletalMuscleMass
        this.gender = gender
        this.age = age
    }

    override fun toModel(): UserHealthDetail = this
}
