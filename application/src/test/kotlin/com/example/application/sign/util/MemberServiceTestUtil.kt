package com.example.application.sign.util

import com.example.core.sign.port.`in`.command.SignUpMemberWithEmailCommand
import com.example.core.user.model.Gender
import com.example.core.user.model.Member
import com.example.core.user.model.MemberInfo
import com.example.core.user.model.Role
import java.time.LocalDateTime
import java.util.UUID

object MemberServiceTestUtil {
    val USER_ID: UUID = UUID.randomUUID()

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

    val member = Member(
        id = USER_ID,
        email = signUpWithEmailCommand.email,
        password = signUpWithEmailCommand.password,
        role = Role.ROLE_FREE,
        socialProvider = null,
        profile = null,
        createdAt = LocalDateTime.now(),
        deletedAt = null,
    )

    val memberInfo = MemberInfo(
        userId = member.id,
        nickname = "",
        tall = 0.0,
        weight = 0.0,
        skeletalMuscleMass = null,
        gender = Gender.FEMALE,
        age = 0,
        exerciseMonths = 0,
    )
}
