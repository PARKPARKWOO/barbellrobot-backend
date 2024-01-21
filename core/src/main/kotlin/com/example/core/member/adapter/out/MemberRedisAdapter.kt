package com.example.core.member.adapter.out

import com.example.core.member.application.`in`.command.SendVerifyEmailCommand
import com.example.core.member.application.`in`.command.VerifyEmailCommand
import com.example.core.member.application.out.EmailVerifyPort
import org.springframework.stereotype.Component

@Component
class MemberRedisAdapter() : EmailVerifyPort {
    override fun verifyEmail(command: VerifyEmailCommand) {
        TODO("Not yet implemented")
    }

    override fun sendVerifyEmail(command: SendVerifyEmailCommand) {
        TODO("Not yet implemented")
    }
}
