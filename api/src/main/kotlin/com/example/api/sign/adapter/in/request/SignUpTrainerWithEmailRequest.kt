package com.example.api.sign.adapter.`in`.request

import com.example.core.sign.application.port.SignUpUserWithEmailCommand
import com.example.core.sign.application.port.`in`.command.SignUpTrainerWithEmailCommand
import com.example.domain.user.Gender
import java.util.UUID

data class SignUpTrainerWithEmailRequest(
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
    fun toCommand(): SignUpUserWithEmailCommand {
        return SignUpTrainerWithEmailCommand(
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
