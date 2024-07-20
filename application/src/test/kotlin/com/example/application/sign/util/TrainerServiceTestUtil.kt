package com.example.application.sign.util

import com.example.core.sign.port.`in`.command.SignUpTrainerWithEmailCommand
import com.example.core.user.model.Gender
import com.example.core.user.model.Role
import com.example.core.user.model.Trainer
import com.example.core.user.model.TrainerInfo
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
        email = signUpWithEmailCommand.email,
        password = signUpWithEmailCommand.password,
        role = Role.ROLE_TRAINER,
    )

    val trainerInfo = TrainerInfo(
        userId = trainer.id,
        gender = Gender.FEMALE,
        street = "",
        country = "",
        city = "",
        exerciseYears = 0,
        nickname = "",
        gymName = "",
        introduce = "",
    )
}
