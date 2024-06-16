package com.example.api.sign.adapter.`in`

import com.example.api.common.annotation.PublicEndPoint
import com.example.api.common.response.ApiResponse
import com.example.api.sign.adapter.`in`.request.SendAuthenticationNumberRequest
import com.example.api.sign.adapter.`in`.request.SignUpMemberWithEmailRequest
import com.example.api.sign.adapter.`in`.request.SignUpTrainerWithEmailRequest
import com.example.api.sign.adapter.`in`.request.VerifyAuthenticationNumberRequest
import com.example.api.sign.adapter.`in`.request.toCommand
import com.example.api.sign.adapter.`in`.response.SuccessAuthenticationResponse
import com.example.api.sign.adapter.`in`.response.VerifyNicknameResponse
import com.example.common.log.Log
import com.example.core.sign.application.port.`in`.EmailVerifyUseCase
import com.example.core.sign.application.port.`in`.SignUpMemberUseCase
import com.example.core.sign.application.port.`in`.SignUpTrainerUseCase
import com.example.core.sign.application.port.`in`.VerifyNicknameUseCase
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
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
    private val verifyNicknameUseCase: VerifyNicknameUseCase,
) : Log {
    @PostMapping("/email/member")
    @Operation(
        summary = "일반 회원 회원가입",
    )
    @PublicEndPoint
    fun signUpWithEmailMember(
        @RequestBody @Valid
        request: SignUpMemberWithEmailRequest,
    ): ApiResponse<Unit> {
        log.info("member")
        signUpMemberUseCase.signUpWithEmail(request.toCommand())
        return ApiResponse(data = Unit)
    }

    @PostMapping("/email/trainer")
    @PublicEndPoint
    @Operation(
        summary = "trainer 회원가입",
    )
    fun signUpWithEmailTrainer(
        @RequestBody @Valid
        request: SignUpTrainerWithEmailRequest,
    ): ApiResponse<Unit> {
        log.info("Trainer")
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
        emailVerifyUseCase.sendVerifyEmail(request.email)
        return ApiResponse(data = Unit)
    }

    @GetMapping("/verify/nickname/{nickname}")
    @PublicEndPoint
    @Operation(
        summary = "회원 가입 시 nickname 검증",
    )
    fun verifyNickname(
        @PathVariable("nickname")
        nickname: String,
    ): ApiResponse<VerifyNicknameResponse> {
        log.info("nickname")
        val response = VerifyNicknameResponse(canNickname = verifyNicknameUseCase.verifyNickname(nickname))
        return ApiResponse(data = response)
    }
}
