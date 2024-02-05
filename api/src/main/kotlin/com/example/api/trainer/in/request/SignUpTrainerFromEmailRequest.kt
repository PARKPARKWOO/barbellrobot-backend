package com.example.api.trainer.`in`.request

import com.example.core.user.trainer.application.`in`.command.SignUpTrainerFromEmailCommand
import com.example.domain.user.Gender
import java.util.*

data class SignUpTrainerFromEmailRequest(
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
    fun toCommand(): SignUpTrainerFromEmailCommand {
        return SignUpTrainerFromEmailCommand(
            email = email,
            nickname = nickname,
            password = password,
            exerciseYears = exerciseYears,
            gymName = gymName,
            street = street,
            city = city,
            country = country,
            introduce = introduce,
            authenticationString = authenticationString,
            gender = gender,
        )
    }
}
