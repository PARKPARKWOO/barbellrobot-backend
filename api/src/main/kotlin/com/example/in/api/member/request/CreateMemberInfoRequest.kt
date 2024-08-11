package com.example.`in`.api.member.request

import com.example.core.user.port.command.SaveMemberInfoCommand
import com.example.core.user.model.Gender
import java.util.UUID

data class CreateMemberInfoRequest(
    val age: Int,
    val exerciseMonths: Int,
    val tall: Double,
    val weight: Double,
    val skeletalMuscleMass: Double?,
    val gender: Gender,
    val nickname: String,
) {
    fun toCommand(memberId: UUID): SaveMemberInfoCommand = SaveMemberInfoCommand(
        memberId = memberId,
        nickname = nickname,
        tall = tall,
        weight = weight,
        skeletalMuscleMass = skeletalMuscleMass,
        gender = gender,
        age = age,
        exerciseMonths = exerciseMonths,
    )
}
