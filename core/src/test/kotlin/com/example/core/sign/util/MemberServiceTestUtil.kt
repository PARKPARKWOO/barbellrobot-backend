package com.example.core.sign.util

import com.example.core.sign.application.port.`in`.command.SignUpMemberWithEmailCommand
import com.example.core.user.member.adapter.out.persistence.entity.MemberEntity
import com.example.core.user.member.adapter.out.persistence.entity.MemberInfo
import com.example.domain.user.Gender
import com.example.domain.user.Role
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

    val memberEntity = MemberEntity(
        email = signUpWithEmailCommand.email,
        nickname = signUpWithEmailCommand.nickname,
        password = signUpWithEmailCommand.password,
        provider = null,
        memberInfo = MemberInfo(
            tall = signUpWithEmailCommand.tall,
            weight = signUpWithEmailCommand.weight,
            skeletalMuscleMass = signUpWithEmailCommand.skeletalMuscleMass,
            age = signUpWithEmailCommand.age,
            exerciseMonths = signUpWithEmailCommand.exerciseMonths,
        ),
        role = Role.ROLE_FREE,
        gender = signUpWithEmailCommand.gender,
        profile = null,
        managementEntity = null,
    )
}
