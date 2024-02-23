package com.example.api.member.adapter.`in`

import com.example.api.common.annotation.PublicEndPoint
import com.example.api.email.adapter.`in`.request.SignInWithEmailRequest
import com.example.api.sign.adapter.`in`.request.SignUpMemberWithEmailRequest
import com.example.api.response.ApiResponse
import com.example.api.response.JwtResponse
import com.example.core.sign.application.port.`in`.SignInMemberUseCase
import com.example.core.sign.application.port.`in`.SignUpMemberUseCase
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/member")
class MemberController(
    private val signInMemberUseCase: SignInMemberUseCase,
) {
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
