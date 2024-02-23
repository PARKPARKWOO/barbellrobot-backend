package com.example.core.sign.util

import com.example.core.sign.application.port.`in`.command.SignUpTrainerWithEmailCommand
import com.example.domain.user.Gender
import java.util.UUID

object TrainerServiceTestUtil {
    val signUpWithEmailCommand = SignUpTrainerWithEmailCommand(
        email = "wy9295@naver.com",
        nickname = "nickname",
        password = "password",
        gender = Gender.FEMALE,
        exerciseYears = 1,
        gymName = "gymName",
        street = "street",
        city = "city",
        country = "korea",
        introduce = "introduce",
        authenticationString = UUID.randomUUID(),
    )
}
