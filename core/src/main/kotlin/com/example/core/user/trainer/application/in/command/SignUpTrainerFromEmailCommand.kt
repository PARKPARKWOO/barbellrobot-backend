package com.example.core.user.trainer.application.`in`.command

import com.example.core.user.application.out.command.VerifyAuthenticationSuccessCommand
import com.example.core.user.trainer.application.out.command.SignUpTrainerCommand
import com.example.domain.user.Gender
import java.util.UUID

data class SignUpTrainerFromEmailCommand(
    val email: String,
    val nickname: String,
    val password: String,
    val gender: Gender,
    val exerciseYears: Int,
    val gymName: String,
    val street: String,
    val city: String,
    val country: String,
    val introduce: String,
    val authenticationString: UUID,
) {
    fun toAuthenticationCommand(): VerifyAuthenticationSuccessCommand {
        return VerifyAuthenticationSuccessCommand(
            email = email,
            authenticationString = authenticationString,
        )
    }

    fun toSignUpCommand(): SignUpTrainerCommand {
        return SignUpTrainerCommand(
            email = email,
            nickname = nickname,
            password = password,
            gender = gender,
            exerciseYears = exerciseYears,
            gymName = gymName,
            street = street,
            city = city,
            country = country,
            introduce = introduce,
        )
    }
}
