package com.example.core.user.adapter.out

import com.example.core.user.application.`in`.command.SendVerifyEmailCommand
import com.example.core.user.application.out.SendEmailPort
import org.springframework.stereotype.Component

@Component
class SendEmailAdapter() : SendEmailPort {
    override fun sendAuthenticationNumber(command: SendVerifyEmailCommand) {
        // TODO email 연동
    }
}
