package com.example.api.member.`in`

import com.example.api.member.`in`.request.SignUpFromEmailRequest
import com.example.api.member.`in`.request.toCommand
import com.example.api.response.ApiResponse
import com.example.core.user.member.application.`in`.SignUpMemberUseCase
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/member")
class MemberController(
    private val signUpMemberUseCase: SignUpMemberUseCase,
) {
    @PostMapping("/sign-up/email")
    @Operation(
        summary = "email 을 통한 회원가입을 합니다.",
    )
    fun signUpMemberWithEmail(
        @RequestBody
        request: SignUpFromEmailRequest,
    ): ApiResponse<Unit> {
        signUpMemberUseCase.signUpMemberFromEmail(request.toCommand())
        return ApiResponse(data = Unit)
    }
}
