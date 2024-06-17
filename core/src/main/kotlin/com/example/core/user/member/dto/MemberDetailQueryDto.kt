package com.example.core.user.member.dto

import com.example.domain.user.Gender
import com.example.domain.user.Provider
import com.example.domain.user.Role
import com.querydsl.core.annotations.QueryProjection
import java.time.LocalDateTime
import java.util.UUID

data class MemberDetailQueryDto
    @QueryProjection
    constructor(
        val id: UUID,
        val email: String,
        val password: String,
        val role: Role,
        val provider: Provider?,
        val createdAt: LocalDateTime,
        val deletedAt: LocalDateTime?,
        val profile: String?,
        val memberInfoQueryDto: MemberInfoQueryDto?,
    )

data class MemberInfoQueryDto
    @QueryProjection
    constructor(
        val gender: Gender?,
        val nickname: String?,
        val exerciseMonths: Int?,
        val tall: Double?,
        val weight: Double?,
        val skeletalMuscleMass: Double?,
        val age: Int?,
    ) {
        fun isNotNull(): Boolean = gender != null
                && nickname != null
                && tall != null
                && weight != null
                && age != null
        }
