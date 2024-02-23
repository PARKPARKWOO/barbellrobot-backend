package com.example.api.email.adapter.`in`

import com.example.api.common.annotation.PublicEndPoint
import com.example.api.email.adapter.`in`.request.SendAuthenticationNumberRequest
import com.example.api.email.adapter.`in`.request.VerifyAuthenticationNumberRequest
import com.example.api.email.adapter.`in`.request.toCommand
import com.example.api.member.adapter.`in`.reponse.SuccessAuthenticationResponse
import com.example.api.response.ApiResponse
import com.example.core.sign.application.port.`in`.EmailVerifyUseCase
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/email")
class EmailController(
    private val emailVerifyUseCase: EmailVerifyUseCase,
) {
    @PostMapping("/verify")
    @Operation(
        summary = "email 인증번호를 검증합니다.",
        description = "email 인증번호를 검증하고 회원가입시 사용할 UUID 를 반환합니다.",
    )
    @PublicEndPoint
    fun authenticateEmail(
        @RequestBody @Valid
        request: VerifyAuthenticationNumberRequest,
    ): ApiResponse<SuccessAuthenticationResponse> {
        val success = emailVerifyUseCase.verifyEmail(request.toCommand())
        val response = SuccessAuthenticationResponse(success)
        return ApiResponse(data = response)
    }

    @PostMapping("/send/email-verify")
    @Operation(
        summary = "email 인증번호를 요청합니다.",
        description = "email 인증을 위한 email 에 인증번호를 전송합니다.",
    )
    @PublicEndPoint
    fun sendAuthenticationNumberEmail(
        @RequestBody @Valid
        request: SendAuthenticationNumberRequest,
    ): ApiResponse<Unit> {
        emailVerifyUseCase.sendVerifyEmail(request.toCommand())
        return ApiResponse(data = Unit)
    }
}
