package com.example.api.member.`in`

import com.example.api.member.`in`.reponse.SuccessAuthenticationResponse
import com.example.api.member.`in`.request.SendAuthenticationNumberRequest
import com.example.api.member.`in`.request.SignUpFromEmailRequest
import com.example.api.member.`in`.request.VerifyAuthenticationNumberRequest
import com.example.api.member.`in`.request.toCommand
import com.example.api.response.ApiResponse
import com.example.core.member.application.`in`.EmailVerifyUseCase
import com.example.core.member.application.`in`.SignUpUseCase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/member")
class MemberController(
    private val emailVerifyUseCase: EmailVerifyUseCase,
    private val signUpUseCase: SignUpUseCase,
) {
    @PostMapping("/email-verify")
    fun authenticateEmail(
        @RequestBody request: VerifyAuthenticationNumberRequest,
    ): ApiResponse<SuccessAuthenticationResponse> {
        val success = emailVerifyUseCase.verifyEmail(request.toCommand())
        val response = SuccessAuthenticationResponse(success)
        return ApiResponse(data = response)
    }

    @PostMapping("/send/email-verify")
    fun sendAuthenticationNumberEmail(
        @RequestBody
        request: SendAuthenticationNumberRequest,
    ): ApiResponse<Unit> {
        emailVerifyUseCase.sendVerifyEmail(request.toCommand())
        return ApiResponse(data = Unit)
    }

    @PostMapping("/sign-up/free")
    fun signUpFreeUser(
        @RequestBody
        request: SignUpFromEmailRequest,
    ): ApiResponse<Unit> {
        signUpUseCase.signUpMemberFromEmail(request.toCommand())
        return ApiResponse(data = Unit)
    }
}
