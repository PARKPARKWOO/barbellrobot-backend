package com.example.core.sign.util

import com.example.core.sign.application.port.`in`.command.SignUpTrainerWithEmailCommand
import com.example.core.user.trainer.adapter.out.persistence.entity.GymAddress
import com.example.core.user.trainer.adapter.out.persistence.entity.TrainerInfo
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
        email = signUpWithEmailCommand.email,
        password = signUpWithEmailCommand.password,
        role = Role.ROLE_TRAINER,
    )

    val trainerInfo = TrainerInfo(
        userId = trainer.id,
        gender = Gender.FEMALE,
        gymAddress = GymAddress(
            street = "",
            country = "",
            city = "",
        ),
        exerciseYears = 0,
        nickname = "",
        gymName = "",
        introduce = "",
    )
}
