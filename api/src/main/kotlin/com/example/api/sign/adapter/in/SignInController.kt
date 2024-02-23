package com.example.api.sign.adapter.`in`

import com.example.api.common.annotation.PublicEndPoint
import com.example.api.common.response.ApiResponse
import com.example.api.sign.adapter.`in`.response.JwtResponse
import com.example.api.sign.adapter.`in`.request.SignInWithEmailRequest
import com.example.core.sign.application.port.`in`.SignInMemberUseCase
import com.example.core.sign.application.port.`in`.SignInTrainerUseCase
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

// 굳이 나눌필요 있을지?? Info 까지 한번에 return??
@RestController
@RequestMapping("/api/v1/sign-in")
class SignInController(
    private val signInTrainerUseCase: SignInTrainerUseCase,
    private val signInMemberUseCase: SignInMemberUseCase,
) {
    @PostMapping("/email/trainer")
    @Operation(
        summary = "email 과 password 로 로그인",
    )
    fun signInTrainerWithEmail(
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

    @PostMapping("/sign-in")
    @Operation(
        summary = "email 과 password 로 로그인",
    )
    @PublicEndPoint
    fun signInMemberWithEmail(
        @RequestBody @Valid
        request: SignInWithEmailRequest,
    ): ApiResponse<JwtResponse> {
        val result = signInMemberUseCase.signInWithEmail(request.toCommand())
        val response = JwtResponse(
            accessToken = result.accessToken,
            refreshToken = result.refreshToken,
        )
        return ApiResponse(data = response)
    }
}
