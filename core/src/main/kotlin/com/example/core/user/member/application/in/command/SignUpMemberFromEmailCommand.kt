package com.example.core.user.member.application.`in`.command

import com.example.core.user.application.out.command.VerifyAuthenticationSuccessCommand
import com.example.domain.user.Gender
import java.util.UUID

data class SignUpMemberFromEmailCommand(
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
) {
    fun toAuthenticationCommand(): VerifyAuthenticationSuccessCommand {
        return VerifyAuthenticationSuccessCommand(
            email = email,
            authenticationString = authenticationString,
        )
    }
}
