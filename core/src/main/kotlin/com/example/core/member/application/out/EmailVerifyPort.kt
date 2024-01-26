package com.example.core.member.application.out

import com.example.core.member.application.`in`.command.SendVerifyEmailCommand
import com.example.core.member.application.`in`.command.SignUpMemberFromEmailCommand
import com.example.core.member.application.`in`.command.VerifyEmailCommand
import java.util.UUID

interface EmailVerifyPort {
    fun verifyEmail(command: VerifyEmailCommand): UUID

    fun saveAuthenticationNumber(command: SendVerifyEmailCommand)

    fun verifyAuthenticationSuccess(command: SignUpMemberFromEmailCommand)
}
