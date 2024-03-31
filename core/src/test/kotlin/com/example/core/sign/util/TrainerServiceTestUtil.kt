package com.example.core.sign.util

import com.example.core.sign.application.port.`in`.command.SignUpTrainerWithEmailCommand
import com.example.domain.user.Gender
import com.example.domain.user.Role
import com.example.domain.user.Trainer
import java.util.UUID

object TrainerServiceTestUtil {
    private val ID = UUID.randomUUID()
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

    val trainer = Trainer(
        id = ID,
        nickname = signUpWithEmailCommand.nickname,
        email = signUpWithEmailCommand.email,
        password = signUpWithEmailCommand.password,
        role = Role.ROLE_TRAINER,
        gender = signUpWithEmailCommand.gender,
        gymName = signUpWithEmailCommand.gymName,
        street = signUpWithEmailCommand.street,
        country = signUpWithEmailCommand.country,
        city = signUpWithEmailCommand.city,
        exerciseYears = signUpWithEmailCommand.exerciseYears,
        introduce = signUpWithEmailCommand.introduce,
    )
}
