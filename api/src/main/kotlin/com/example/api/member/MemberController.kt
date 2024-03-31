package com.example.api.member

import com.example.api.common.annotation.AuthenticationUser
import com.example.api.common.config.SwaggerConfig
import com.example.api.common.resolver.UserInfo
import com.example.api.common.response.ApiResponse
import com.example.api.member.request.AddGoalRequest
import com.example.api.member.request.DeleteMemberGoalRequest
import com.example.api.member.request.UpdateMemberInfoRequest
import com.example.core.user.application.port.command.UploadProfileCommand
import com.example.core.user.member.application.`in`.MemberGoalUseCase
import com.example.core.user.member.application.`in`.MemberUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1/member")
class MemberController(
    private val memberUseCase: MemberUseCase,
    private val memberGoalUseCase: MemberGoalUseCase,
) {
    @PostMapping("/profile")
    @Operation(
        summary = "profile 업로드",
        security = [SecurityRequirement(name = SwaggerConfig.AUTHORIZATION_BEARER_SECURITY_SCHEME_NAME)],
    )
    fun uploadProfile(
        @AuthenticationUser
        @Parameter(hidden = true)
        userInfo: UserInfo,
        multipartFile: MultipartFile,
    ): ApiResponse<Unit> {
        val command = UploadProfileCommand(
            id = userInfo.userId,
            file = multipartFile,
        )
        memberUseCase.uploadProfile(command)
        return ApiResponse(data = Unit)
    }

    @PostMapping("/goal")
    @Operation(
        summary = "user 의 목표를 추가합니다.",
        security = [SecurityRequirement(name = SwaggerConfig.AUTHORIZATION_BEARER_SECURITY_SCHEME_NAME)],
    )
    fun addGoal(
        @AuthenticationUser
        @Parameter(hidden = true)
        userInfo: UserInfo,
        @RequestBody
        request: AddGoalRequest,
    ): ApiResponse<Unit> {
        memberGoalUseCase.addGoal(request.toCommand(userInfo.userId))
        return ApiResponse(data = Unit)
    }

    @PutMapping
    @Operation(
        summary = "회원 정보 일괄 수정",
        security = [SecurityRequirement(name = SwaggerConfig.AUTHORIZATION_BEARER_SECURITY_SCHEME_NAME)],
    )
    fun updateMemberInfo(
        @AuthenticationUser
        @Parameter(hidden = true)
        userInfo: UserInfo,
        @RequestBody
        request: UpdateMemberInfoRequest,
    ): ApiResponse<Unit> {
        memberUseCase.update(request.toCommand(userInfo.userId))
        return ApiResponse(data = Unit)
    }

    @DeleteMapping("/goal")
    @Operation(
        summary = "사용자의 관심사 삭제",
        security = [SecurityRequirement(name = SwaggerConfig.AUTHORIZATION_BEARER_SECURITY_SCHEME_NAME)],
    )
    fun deleteGoal(
        @AuthenticationUser
        @Parameter(hidden = true)
        userInfo: UserInfo,
        @RequestBody
        request: DeleteMemberGoalRequest,
    ): ApiResponse<Unit> {
        memberGoalUseCase.deleteGoal(request.toCommand(userInfo.userId))
        return ApiResponse(data = Unit)
    }
}
