package com.example.domain.user

import java.time.LocalDateTime
import java.util.UUID

data class Member(
    val id: UUID,
    val nickname: String,
    val email: String,
    val password: String,
    val role: Role,
    val gender: Gender,
    val provider: String?,
    val exerciseMonths: Int,
    val tall: Double,
    val weight: Double,
    val skeletalMuscleMass: Double?, // 골격근량
    val age: Int,
    val createdAt: LocalDateTime,
    val deletedAt: LocalDateTime?,
) : User {
    override fun getClaims(): Map<String, Any> {
        val claims = mutableMapOf<String, Any>()
        claims["id"] = id
        claims["role"] = role
        return claims
    }
}
