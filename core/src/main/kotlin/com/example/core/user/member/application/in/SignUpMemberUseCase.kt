package com.example.core.user.member.application.`in`

import com.example.core.user.application.`in`.command.SignUpMemberFromEmailCommand

interface SignUpMemberUseCase {
    fun signUpMemberFromEmail(command: SignUpMemberFromEmailCommand)
}
