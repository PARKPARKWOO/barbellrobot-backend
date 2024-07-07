package com.example.core.sign.application.port

import com.example.core.mail.application.port.out.command.VerifyAuthenticationSuccessCommand
import com.example.core.user.model.Gender

interface SignUpUserWithEmailCommand {
    val nickname: String
    val email: String
    val password: String
    val gender: Gender

    fun toAuthenticationCommand(): VerifyAuthenticationSuccessCommand
}
