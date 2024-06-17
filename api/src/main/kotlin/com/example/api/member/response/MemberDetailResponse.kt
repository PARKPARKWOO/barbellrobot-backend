package com.example.api.member.response

import com.example.core.user.member.dto.MemberDetailQueryDto
import com.example.domain.user.Gender
import com.example.domain.user.Role

data class MemberDetailResponse(
    val profile: String?,
    val role: Role,
    val memberInfoResponse: MemberInfoResponse?,
) {
    companion object {
        fun from(dto: MemberDetailQueryDto): MemberDetailResponse = dto
            .memberInfoQueryDto?.let {
            if (it.isNotNull()) {
                MemberDetailResponse(
                    profile = dto.profile,
                    role = dto.role,
                    memberInfoResponse = MemberInfoResponse(
                        nickname = it.nickname!!,
                        tall = it.tall!!,
                        weight = it.weight!!,
                        skeletalMuscleMass = it.skeletalMuscleMass,
                        gender = it.gender!!,
                        age = it.age!!,
                        exerciseMonths = it.exerciseMonths!!,
                    ),
                )
            } else {
                MemberDetailResponse(
                    profile = dto.profile,
                    role = dto.role,
                    memberInfoResponse = null,
                )
            }
        } ?: MemberDetailResponse(
            profile = dto.profile,
            role = dto.role,
            memberInfoResponse = null,
        )
    }
}

data class MemberInfoResponse(
    val nickname: String,
    val tall: Double,
    val weight: Double,
    val skeletalMuscleMass: Double?,
    val gender: Gender,
    val age: Int,
    val exerciseMonths: Int,
)
