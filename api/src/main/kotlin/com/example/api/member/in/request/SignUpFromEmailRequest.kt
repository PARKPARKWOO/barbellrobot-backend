package com.example.api.member.`in`.request

import com.example.core.member.application.`in`.command.SignUpMemberFromEmailCommand
import java.util.UUID

data class SignUpFromEmailRequest(
    val email: String,
    val nickname: String,
    val password: String,
    val gender: String,
    val age: Int,
    val exerciseMonths: Int,
    val tall: Double,
    val weight: Double,
    val skeletalMuscleMass: Double?,
    val authenticationString: UUID,
) {
    fun toCommand(): SignUpMemberFromEmailCommand {
        return SignUpMemberFromEmailCommand(
            email = email,
            nickname = nickname,
            password = password,
            gender = gender,
            age = age,
            exerciseMonths = exerciseMonths,
            tall = tall,
            weight = weight,
            skeletalMuscleMass = skeletalMuscleMass,
            authenticationString = authenticationString,
        )
    }
}
