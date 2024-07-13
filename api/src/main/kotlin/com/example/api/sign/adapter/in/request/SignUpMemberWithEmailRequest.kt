package com.example.api.sign.adapter.`in`.request

import com.example.core.sign.port.`in`.command.SignUpUserWithEmailCommand
import com.example.core.sign.port.`in`.command.SignUpMemberWithEmailCommand
import com.example.core.user.model.Gender
import org.jetbrains.annotations.NotNull
import java.util.UUID

data class SignUpMemberWithEmailRequest(
    @field:NotNull
    val email: String,
    @field:NotNull
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
    fun toCommand(): SignUpUserWithEmailCommand {
        return SignUpMemberWithEmailCommand(
            email = email,
            nickname = nickname,
            password = password,
            gender = gender,
            age = age,
            exerciseMonths = exerciseMonths,
            tall = tall,
            weight = weight,
            skeletalMuscleMass = skeletalMuscleMass,
            authenticationString = authenticationString,
        )
    }
}
