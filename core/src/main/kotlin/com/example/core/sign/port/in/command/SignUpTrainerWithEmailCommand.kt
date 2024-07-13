package com.example.core.sign.port.`in`.command

import com.example.core.mail.application.port.command.VerifyAuthenticationSuccessCommand
import com.example.core.user.port.command.SignUpTrainerCommand
import com.example.core.user.model.Gender
import java.util.UUID

data class SignUpTrainerWithEmailCommand(
    override val email: String,
    override val nickname: String,
    override val password: String,
    override val gender: Gender,
    val exerciseYears: Int,
    val gymName: String,
    val street: String,
    val city: String,
    val country: String,
    val introduce: String,
    val authenticationString: UUID,
) : SignUpUserWithEmailCommand {
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

    override fun toAuthenticationCommand(): VerifyAuthenticationSuccessCommand {
        return VerifyAuthenticationSuccessCommand(
            email = email,
            authenticationString = authenticationString,
        )
    }
}
