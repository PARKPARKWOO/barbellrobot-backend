package com.example.core.member.util

import com.example.core.sign.application.port.`in`.command.SignUpMemberWithEmailCommand
import com.example.domain.user.Gender
import java.util.UUID

object MemberServiceTestUtil {
    val signUpWithEmailCommand = SignUpMemberWithEmailCommand(
        email = "wy9295@naver.com",
        nickname = "nickname",
        password = "password",
        gender = Gender.FEMALE,
        age = 1,
        exerciseMonths = 1,
        tall = 1.1,
        weight = 1.1,
        skeletalMuscleMass = null,
        authenticationString = UUID.randomUUID(),
    )
}
