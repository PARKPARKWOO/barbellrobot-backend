package com.example.core.member.application.`in`

import com.example.core.member.application.`in`.command.SignUpMemberFromEmailCommand

interface SignUpUseCase {
    fun signUpMemberFromEmail(command: SignUpMemberFromEmailCommand)
}
