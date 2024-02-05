package com.example.core.user.member.application.`in`

import com.example.core.user.member.application.`in`.command.SignUpMemberFromEmailCommand
import com.example.core.user.member.application.`in`.command.SignUpMemberFromKakaoCommand

interface SignUpMemberUseCase {
    fun signUpMemberFromEmail(command: SignUpMemberFromEmailCommand)

    fun signUpMemberFromKakao(command: SignUpMemberFromKakaoCommand)
}
