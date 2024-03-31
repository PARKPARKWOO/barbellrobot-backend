package com.example.core.user.member.application.command

import com.example.domain.user.Gender
import java.util.UUID

data class UpdateMemberInfoCommand(
    val id: UUID,
    val nickname: String,
    val gender: Gender,
    val age: Int,
    val tall: Double,
    val weight: Double,
    val skeletalMuscleMass: Double,
    val exerciseMonths: Int,
)
