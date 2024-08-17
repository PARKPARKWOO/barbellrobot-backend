package com.example.`in`.api.trainer

import com.example.`in`.common.annotation.AuthenticationUser
import com.example.`in`.common.config.SwaggerConfig
import com.example.`in`.common.resolver.UserInfo
import com.example.`in`.common.response.ApiResponse
import com.example.core.managemnet.port.`in`.ManagementUseCase
import com.example.core.user.port.command.UploadProfileCommand
import com.example.core.user.port.`in`.TrainerUseCase
import com.example.`in`.api.trainer.request.ApprovalRequest
import com.example.`in`.api.trainer.request.RejectRequest
import com.example.`in`.api.trainer.response.ManagementMemberResponse
import com.example.`in`.api.trainer.response.MemberSummaryResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1/trainer")
class TrainerController(
    private val managementUseCase: ManagementUseCase,
    private val trainerUseCase: TrainerUseCase,

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

    @PostMapping("/management/reject")
    @Operation(
        summary = "pt 제안 거절",
        security = [SecurityRequirement(name = SwaggerConfig.AUTHORIZATION_BEARER_SECURITY_SCHEME_NAME)],
    )
    fun reject(
        @Parameter(hidden = true) @AuthenticationUser
        userInfo: UserInfo,
        @RequestBody
        request: RejectRequest,
    ): ApiResponse<Unit> {
        managementUseCase.reject(request.toCommand(userInfo.userId))
        return ApiResponse(data = Unit)
    }

    @PostMapping("/management/approval")
    @Operation(
        summary = "pt 제안 승인",
        security = [SecurityRequirement(name = SwaggerConfig.AUTHORIZATION_BEARER_SECURITY_SCHEME_NAME)],
    )
    fun approval(
        @Parameter(hidden = true) @AuthenticationUser
        userInfo: UserInfo,
        @RequestBody
        request: ApprovalRequest,
    ): ApiResponse<Unit> {
        managementUseCase.approval(request.toCommand(userInfo.userId))
        return ApiResponse(data = Unit)
    }

    @PostMapping("/profile")
    @Operation(
        summary = "profile 저장",
        security = [SecurityRequirement(name = SwaggerConfig.AUTHORIZATION_BEARER_SECURITY_SCHEME_NAME)],
    )
    fun uploadProfile(
        @AuthenticationUser
        @Parameter(hidden = true)
        userInfo: UserInfo,
        @RequestParam("profile")
        multipartFile: MultipartFile,
    ): ApiResponse<Unit> {
        val command = UploadProfileCommand(
            id = userInfo.userId,
            contentLength = multipartFile.size,
            contentType = multipartFile.contentType!!,
            inputStream = multipartFile.inputStream,
        )
        trainerUseCase.uploadProfile(command)
        return ApiResponse(data = Unit)
    }
}
