package com.example.core.sign.adapter.out

import com.example.core.sign.application.port.`in`.command.SendVerifyEmailCommand
import com.example.core.sign.application.port.out.SendEmailPort
import org.springframework.stereotype.Component

@Component
class SendEmailAdapter : SendEmailPort {
    override fun sendAuthenticationNumber(command: SendVerifyEmailCommand) {
        // TODO email 연동
    }
}
