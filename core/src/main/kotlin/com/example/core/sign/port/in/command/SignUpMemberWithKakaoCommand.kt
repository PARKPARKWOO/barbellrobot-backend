package com.example.core.sign.port.`in`.command

import com.example.core.user.model.Gender
import java.util.UUID

data class SignUpMemberWithKakaoCommand(
    val email: String,
    val nickname: String,
    val password: String,
    val gender: Gender,
    val age: Int,
    val exerciseMonths: Int,
    val tall: Double,
    val weight: Double,
    val skeletalMuscleMass: Double?,
    val authenticationString: UUID,
)
