package com.example.api.sign.adapter.`in`

import com.example.api.common.annotation.PublicEndPoint
import com.example.api.common.response.ApiResponse
import com.example.api.sign.adapter.`in`.request.ReissueTokenRequest
import com.example.api.sign.adapter.`in`.request.SignInWithEmailRequest
import com.example.api.sign.adapter.`in`.response.JwtResponse
import com.example.common.log.Log
import com.example.core.jwt.application.port.`in`.JwtReissueUseCase
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
    private val reissueUseCase: JwtReissueUseCase,
) : Log {
    @PostMapping("/email/trainer")
    @Operation(
        summary = "email 과 password 로 로그인",
    )
    @PublicEndPoint
    fun signInTrainerWithEmail(
        @RequestBody
        request: SignInWithEmailRequest,
    ): ApiResponse<JwtResponse> {
        val response = JwtResponse.from(signInTrainerUseCase.signInWithEmail(request.toCommand()))
        return ApiResponse(data = response)
    }

    @PostMapping("/email/member")
    @Operation(
        summary = "email 과 password 로 로그인",
    )
    @PublicEndPoint
    fun signInMemberWithEmail(
        @RequestBody @Valid
        request: SignInWithEmailRequest,
    ): ApiResponse<JwtResponse> {
        log.info("요청 들어옴")
        val response = JwtResponse.from(signInMemberUseCase.signInWithEmail(request.toCommand()))
        return ApiResponse(data = response)
    }

    @PostMapping("/reissue")
    @Operation(
        summary = "JWT Token 재발급",
    )
    @PublicEndPoint
    fun reissueToken(
        @RequestBody
        request: ReissueTokenRequest,
    ): ApiResponse<JwtResponse> {
        log.info("reissue")
        val response = JwtResponse.from(reissueUseCase.reissueToken(request.refreshToken))
        return ApiResponse(data = response)
    }
}
