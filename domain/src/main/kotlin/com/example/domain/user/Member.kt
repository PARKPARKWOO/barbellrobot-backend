package com.example.domain.user

import java.time.LocalDateTime

data class Member(
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
)
