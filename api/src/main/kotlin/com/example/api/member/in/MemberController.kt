package com.example.api.member.`in`

import com.example.api.member.`in`.request.SendAuthenticationNumberRequest
import com.example.api.member.`in`.request.VerifyAuthenticationNumberRequest
import com.example.api.member.`in`.request.toCommand
import com.example.core.member.application.`in`.EmailVerifyUseCase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/member")
class MemberController(
    private val emailVerifyUseCase: EmailVerifyUseCase,
) {
    @PostMapping("/email-verify")
    fun authenticateEmail(
        @RequestBody request: VerifyAuthenticationNumberRequest,
    ) {
        emailVerifyUseCase.verifyEmail(request.toCommand())
    }

    @PostMapping("/send/email-verify")
    fun sendAuthenticationNumberEmail(
        @RequestBody
        request: SendAuthenticationNumberRequest,
    ) {
        emailVerifyUseCase.sendVerifyEmail(request.toCommand())
    }

    @PostMapping("/sign-up/free")
    fun signUpFreeUser() {
    }
}
