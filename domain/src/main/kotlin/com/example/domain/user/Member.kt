package com.example.domain.user

import java.time.LocalDateTime
import java.util.UUID

data class Member(
    val id: UUID,
    val email: String,
    val nickname: String,
    val password: String,
    val provider: String?,
    val exerciseMonths: Int,
    val tall: Double,
    val weight: Double,
    val skeletalMuscleMass: Double?, // 골격근량
    val age: Int,
    val gender: Gender,
    val createdAt: LocalDateTime,
    val deletedAt: LocalDateTime?,
    val role: Role,
) {
    fun getClaims(): Map<String, Any> {
        val claims = mutableMapOf<String, Any>()
        claims["id"] = id
        claims["role"] = role
        return claims
    }
}
