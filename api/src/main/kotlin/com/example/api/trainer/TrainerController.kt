package com.example.api.trainer

import com.example.api.common.annotation.AuthenticationUser
import com.example.api.common.config.SwaggerConfig
import com.example.api.common.resolver.UserInfo
import com.example.api.common.response.ApiResponse
import com.example.api.trainer.request.AddManagementMemberRequest
import com.example.api.trainer.response.ManagementMemberResponse
import com.example.api.trainer.response.MemberSummaryResponse
import com.example.core.managemnet.application.port.command.AddManagementMemberCommand
import com.example.core.managemnet.application.port.`in`.ManagementUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/trainer")
class TrainerController(
    private val managementUseCase: ManagementUseCase,
) {
    // 페이지 네이션 필요한지?
    // Trainer 만 사용 가능하도록
    @GetMapping("/management")
    @Operation(
        summary = "관리하고 있는 member 목록",
        security = [SecurityRequirement(name = SwaggerConfig.AUTHORIZATION_BEARER_SECURITY_SCHEME_NAME)],
    )
    fun getManagementMemberList(
        @Parameter(hidden = true) @AuthenticationUser
        userInfo: UserInfo,
    ): ApiResponse<ManagementMemberResponse> {
        val memberSummary = managementUseCase.getMemberSummary(userInfo.userId).map {
            MemberSummaryResponse.from(it)
        }
        val response = ManagementMemberResponse(memberSummary)
        return ApiResponse(data = response)
    }

    @PostMapping("/management")
    @Operation(
        summary = "관리 목록에 member 추가",
        security = [SecurityRequirement(name = SwaggerConfig.AUTHORIZATION_BEARER_SECURITY_SCHEME_NAME)],
    )
    fun addManagementMember(
        @Parameter(hidden = true) @AuthenticationUser
        userInfo: UserInfo,
        @RequestBody
        request: AddManagementMemberRequest,
    ): ApiResponse<Unit> {
        val command = AddManagementMemberCommand(
            trainerId = userInfo.userId,
            memberId = request.memberId,
        )
        managementUseCase.addManagementMember(command)
        return ApiResponse(data = Unit)
    }
}
