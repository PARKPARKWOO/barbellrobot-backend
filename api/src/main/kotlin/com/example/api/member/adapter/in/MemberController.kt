package com.example.api.member.adapter.`in`

import com.example.api.common.annotation.PublicEndPoint
import com.example.api.email.adapter.`in`.request.SignInWithEmailRequest
import com.example.api.email.adapter.`in`.request.SignUpFromEmailRequest
import com.example.api.response.ApiResponse
import com.example.api.response.JwtResponse
import com.example.core.user.member.application.`in`.SignInMemberUseCase
import com.example.core.user.member.application.`in`.SignUpMemberUseCase
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/member")
class MemberController(
    private val signUpMemberUseCase: SignUpMemberUseCase,
    private val signInMemberUseCase: SignInMemberUseCase,
) {
    @PostMapping("/sign-up/email")
    @Operation(
        summary = "email 을 통한 회원가입을 합니다.",
    )
    @PublicEndPoint
    fun signUpMemberWithEmail(
        @RequestBody @Valid
        request: SignUpFromEmailRequest,
    ): ApiResponse<Unit> {
        signUpMemberUseCase.signUpMemberFromEmail(request.toCommand())
        return ApiResponse(data = Unit)
    }

    @PostMapping("/sign-in")
    @Operation(
        summary = "email 과 password 로 로그인",
    )
    @PublicEndPoint
    fun signInWithEmail(
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

    @PostMapping("/sign-up/kakao")
    @Operation(
        summary = "member Kakao 회원가입",
    )
    @PublicEndPoint
    fun signUpWithKakao(
//        @RequestBody
//        request: SignUpMemberWithKakaoRequest,
    ) {
        //
    }
}
