package com.example.infrastructure.adapter.mail

import com.example.core.mail.application.port.out.SendEmailPort
import com.example.core.sign.port.`in`.command.SendVerifyEmailCommand
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component

@Component
class SendEmailAdapter(
    private val javaMailSender: JavaMailSender,
    @Value("\${spring.mail.username}")
    private val sender: String,
) : SendEmailPort {
    override fun sendAuthenticationNumber(command: SendVerifyEmailCommand) {
        val simpleMessage = SimpleMailMessage()
        simpleMessage.from = sender
        simpleMessage.setTo(command.email)
        simpleMessage.subject = "바벨로봇 인증메일 입니다."
        simpleMessage.text = "인증번호: ${command.authenticationNumber}"
        javaMailSender.send(simpleMessage)
    }
}
