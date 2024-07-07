package com.example.api.member.request

import com.example.core.user.member.application.command.UpdateMemberInfoCommand
import com.example.core.user.model.Gender
import java.util.UUID

data class UpdateMemberInfoRequest(
    val nickname: String,
    val gender: Gender,
    val age: Int,
    val tall: Double,
    val weight: Double,
    val skeletalMuscleMass: Double,
    val exerciseMonths: Int,
) {
    fun toCommand(id: UUID) = UpdateMemberInfoCommand(
        nickname = nickname,
        gender = gender,
        age = age,
        tall = tall,
        weight = weight,
        skeletalMuscleMass = skeletalMuscleMass,
        exerciseMonths = exerciseMonths,
        id = id,
    )
}
