package com.example.api.member

import com.example.api.common.annotation.AuthenticationUser
import com.example.api.common.annotation.RateLimit
import com.example.api.common.config.SwaggerConfig
import com.example.api.common.resolver.UserInfo
import com.example.api.common.response.ApiResponse
import com.example.api.member.request.AddGoalRequest
import com.example.api.member.request.CancelManagementRequest
import com.example.api.member.request.CreateMemberInfoRequest
import com.example.api.member.request.DeleteMemberGoalRequest
import com.example.api.member.request.OfferManagementRequest
import com.example.api.member.request.UpdateMemberInfoRequest
import com.example.api.member.response.GetMemberMyPageResponse
import com.example.api.member.response.ManagementFromMemberResponse
import com.example.api.member.response.MemberDetailResponse
import com.example.core.managemnet.port.`in`.ManagementUseCase
import com.example.core.user.port.command.UploadProfileCommand
import com.example.core.user.port.`in`.MemberGoalUseCase
import com.example.core.user.port.`in`.MemberInfoUseCase
import com.example.core.user.port.`in`.MemberUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.util.concurrent.TimeUnit

@RestController
@RequestMapping("/api/v1/member")
class MemberController(
    private val memberUseCase: MemberUseCase,
    private val memberGoalUseCase: MemberGoalUseCase,
    private val managementUseCase: ManagementUseCase,
    private val memberInfoUseCase: MemberInfoUseCase,
) {
    @PostMapping(path = ["/profile"], consumes = ["multipart/form-data"])
    @Operation(
        summary = "profile 업로드",
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
            contentType = multipartFile.contentType!!,
            contentLength = multipartFile.size,
            inputStream = multipartFile.inputStream,
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

    @PostMapping("/management/offer")
    @Operation(
        summary = "trainer 에게 제의 pt 제의",
        description = "member 가 trainer 에게 pt 를 제안 하는 api 입니다.",
        security = [SecurityRequirement(name = SwaggerConfig.AUTHORIZATION_BEARER_SECURITY_SCHEME_NAME)],
    )
    fun offerManagement(
        @Parameter(hidden = true) @AuthenticationUser
        userInfo: UserInfo,
        @RequestBody
        request: OfferManagementRequest,
    ): ApiResponse<Unit> {
        managementUseCase.offer(request.toCommand(userInfo.userId))
        return ApiResponse(data = Unit)
    }

    @PostMapping("/management/cancel")
    @Operation(
        summary = "pt 제의 취소",
        security = [SecurityRequirement(name = SwaggerConfig.AUTHORIZATION_BEARER_SECURITY_SCHEME_NAME)],
    )
    fun cancelManagement(
        @Parameter(hidden = true) @AuthenticationUser
        userInfo: UserInfo,
        @RequestBody
        request: CancelManagementRequest,
    ): ApiResponse<Unit> {
        managementUseCase.cancel(request.toCommand(userInfo.userId))
        return ApiResponse(data = Unit)
    }

    @GetMapping("/management")
    @Operation(
        summary = "pt 목록 조회",
        security = [SecurityRequirement(name = SwaggerConfig.AUTHORIZATION_BEARER_SECURITY_SCHEME_NAME)],
    )
    fun getManagement(
        @Parameter(hidden = true) @AuthenticationUser
        userInfo: UserInfo,
    ): ApiResponse<List<ManagementFromMemberResponse>> {
        val response = managementUseCase.getManagementFromMember(userInfo.userId).map {
            ManagementFromMemberResponse.from(it)
        }
        return ApiResponse(data = response)
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

    @GetMapping
    @Operation(
        summary = "member 정보 조회",
        security = [SecurityRequirement(name = SwaggerConfig.AUTHORIZATION_BEARER_SECURITY_SCHEME_NAME)],
    )
    fun getMemberInfo(
        @AuthenticationUser
        @Parameter(hidden = true)
        userInfo: UserInfo,
    ): ApiResponse<MemberDetailResponse> {
        val response = MemberDetailResponse.from(memberInfoUseCase.getDetail(userInfo.userId))
        return ApiResponse(data = response)
    }

    @GetMapping("/me")
    @Operation(
        summary = "member detail, goal",
        description = "my page 접속할때 사용",
        security = [SecurityRequirement(name = SwaggerConfig.AUTHORIZATION_BEARER_SECURITY_SCHEME_NAME)],
    )
    fun myPage(
        @AuthenticationUser
        @Parameter(hidden = true)
        userInfo: UserInfo,
    ): ApiResponse<GetMemberMyPageResponse> {
        val response = GetMemberMyPageResponse.from(memberUseCase.getMemberAndGoal(userInfo.userId))
        return ApiResponse(data = response)
    }

    @PostMapping
    @Operation(
        summary = "member Info 추가",
        description = "소셜 로그인 시 디테일 한 정보를 입력받아야함",
        security = [SecurityRequirement(name = SwaggerConfig.AUTHORIZATION_BEARER_SECURITY_SCHEME_NAME)],
    )
    @RateLimit(quota = 1, timeUnit = TimeUnit.SECONDS, refillInterval = 3, refillTokens = 1)
    fun createMemberInfo(
        @AuthenticationUser
        @Parameter(hidden = true)
        userInfo: UserInfo,
        @RequestBody
        request: CreateMemberInfoRequest,
    ): ApiResponse<Unit> {
        memberInfoUseCase.save(request.toCommand(userInfo.userId))
        return ApiResponse(data = Unit)
    }
}
