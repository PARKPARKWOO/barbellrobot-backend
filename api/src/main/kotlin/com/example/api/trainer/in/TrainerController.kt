package com.example.api.trainer.`in`

import com.example.api.email.`in`.request.SignInWithEmailRequest
import com.example.api.response.ApiResponse
import com.example.api.response.JwtResponse
import com.example.api.trainer.`in`.request.SignUpTrainerFromEmailRequest
import com.example.common.annotation.PublicEndPoint
import com.example.core.user.trainer.application.`in`.SignInTrainerUseCase
import com.example.core.user.trainer.application.`in`.SignUpTrainerUseCase
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/trainer")
class TrainerController(
    private val trainerUseCase: SignUpTrainerUseCase,
    private val signInTrainerUseCase: SignInTrainerUseCase,
) {
    @PostMapping("/sign-up/email")
    @Operation(
        summary = "email 을 사용한 회원가입",
    )
    @PublicEndPoint
    fun signUpTrainerWithEmail(
        @RequestBody
        request: SignUpTrainerFromEmailRequest,
    ): ApiResponse<Unit> {
        trainerUseCase.signUpTrainerFromEmail(request.toCommand())
        return ApiResponse(data = Unit)
    }

    @PostMapping("/sign-in")
    @Operation(
        summary = "email 과 password 로 로그인",
    )
    fun signInWithEmail(
        @RequestBody
        request: SignInWithEmailRequest,
    ): ApiResponse<JwtResponse> {
        val result = signInTrainerUseCase.signInWithEmail(request.toCommand())
        val response = JwtResponse(
            accessToken = result.accessToken,
            refreshToken = result.refreshToken,
        )
        return ApiResponse(data = response)
    }
}
