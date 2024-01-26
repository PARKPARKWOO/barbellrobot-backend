package com.example.core.member.adapter

import com.example.core.member.application.`in`.command.SendVerifyEmailCommand
import com.example.core.member.application.out.SendEmailPort
import org.springframework.stereotype.Component

@Component
class SendEmailAdapter() : SendEmailPort {
    override fun sendAuthenticationNumber(command: SendVerifyEmailCommand) {
        // TODO email 연동
    }
}
