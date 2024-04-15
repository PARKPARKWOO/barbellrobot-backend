package com.example.core.sign.application.port.`in`.command

import com.example.core.sign.application.port.SignUpUserWithEmailCommand
import com.example.core.mail.application.port.out.command.VerifyAuthenticationSuccessCommand
import com.example.domain.user.Gender
import java.util.UUID

data class SignUpMemberWithEmailCommand(
    override val email: String,
    override val nickname: String,
    override val password: String,
    override val gender: Gender,
    val age: Int,
    val exerciseMonths: Int,
    val tall: Double,
    val weight: Double,
    val skeletalMuscleMass: Double?,
    val authenticationString: UUID,
) : SignUpUserWithEmailCommand {
    override fun toAuthenticationCommand(): VerifyAuthenticationSuccessCommand {
        return VerifyAuthenticationSuccessCommand(
            email = email,
            authenticationString = authenticationString,
        )
    }
}
