package com.example.api.sign.adapter.`in`

import com.example.api.common.annotation.PublicEndPoint
import com.example.api.response.ApiResponse
import com.example.api.sign.adapter.`in`.request.SendAuthenticationNumberRequest
import com.example.api.sign.adapter.`in`.request.SignUpMemberWithEmailRequest
import com.example.api.sign.adapter.`in`.request.SignUpTrainerWithEmailRequest
import com.example.api.sign.adapter.`in`.request.VerifyAuthenticationNumberRequest
import com.example.api.sign.adapter.`in`.request.toCommand
import com.example.api.sign.adapter.`in`.response.SuccessAuthenticationResponse
import com.example.core.sign.application.port.`in`.EmailVerifyUseCase
import com.example.core.sign.application.port.`in`.SignUpMemberUseCase
import com.example.core.sign.application.port.`in`.SignUpTrainerUseCase
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/sign-up")
class SignUpController(
    private val signUpMemberUseCase: SignUpMemberUseCase,
    private val signUpTrainerUseCase: SignUpTrainerUseCase,
    private val emailVerifyUseCase: EmailVerifyUseCase,
) {
    @PostMapping("/email/member")
    fun signUpMember(
        @RequestBody @Valid
        request: SignUpMemberWithEmailRequest,
    ): ApiResponse<Unit> {
        signUpMemberUseCase.signUpWithEmail(request.toCommand())
        return ApiResponse(data = Unit)
    }

    @PostMapping("/email/trainer")
    fun signUpTrainer(
        @RequestBody @Valid
        request: SignUpTrainerWithEmailRequest,
    ): ApiResponse<Unit> {
        signUpTrainerUseCase.signUpWithEmail(request.toCommand())
        return ApiResponse(data = Unit)
    }

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

    @PostMapping("/kakao/member")
    @Operation(
        summary = "member Kakao 회원가입",
    )
    @PublicEndPoint
    fun signUpWithKakao(
//        @RequestBody
//        request: SignUpMemberWithKakaoRequest,
    ) {
        signUpMemberUseCase.signUpWithKakao()
    }
}
