package com.example.core.member.application.out

import com.example.core.member.application.`in`.command.SignUpMemberFromEmailCommand

interface SignUpPort {
    fun signUpMemberFromEmail(command: SignUpMemberFromEmailCommand)
}
