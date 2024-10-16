package com.example.`in`.api.sign.adapter.`in`

import com.example.application.common.log.logger
import com.example.core.sign.port.`in`.JwtReissueUseCase
import com.example.core.sign.port.`in`.SignInMemberUseCase
import com.example.core.sign.port.`in`.SignInTrainerUseCase
import com.example.`in`.api.sign.adapter.`in`.request.ReissueTokenRequest
import com.example.`in`.api.sign.adapter.`in`.request.SignInWithEmailRequest
import com.example.`in`.api.sign.adapter.`in`.request.SignInWithKakaoRequest
import com.example.`in`.api.sign.adapter.`in`.response.JwtResponse
import com.example.`in`.common.annotation.PublicEndPoint
import com.example.`in`.common.response.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import org.springframework.validation.annotation.Validated
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
) {
    private val log = logger()

    @PostMapping("/email/trainer")
    @Operation(
        summary = "email 과 password 로 로그인",
    )
    @PublicEndPoint
    fun signInTrainerWithEmail(
        @RequestBody @Validated
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
        @RequestBody @Validated
        request: SignInWithEmailRequest,
    ): ApiResponse<JwtResponse> {
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

    @PostMapping("/kakao/member")
    @PublicEndPoint
    fun signInWithKakao(
        @RequestBody
        request: SignInWithKakaoRequest,
    ): ApiResponse<JwtResponse> {
        val response = JwtResponse.from(signInMemberUseCase.signInWithKakao(request.toCommand()))
        return ApiResponse(data = response)
    }
}
