package com.example.core.user.member.application.command

import com.example.core.user.model.Gender
import java.util.UUID

data class SaveMemberInfoCommand(
    val memberId: UUID,
    val nickname: String,
    val tall: Double,
    val weight: Double,
    val skeletalMuscleMass: Double?,
    val gender: Gender,
    val age: Int,
    val exerciseMonths: Int,
)
