package com.example.`in`.api.rival.adapter.`in`

import com.example.core.rival.port.`in`.RivalUseCase
import com.example.core.user.port.`in`.MemberUseCase
import com.example.`in`.api.rival.adapter.`in`.request.UpdateRivalStatusRequest
import com.example.`in`.api.rival.adapter.`in`.response.RivalSummaryResponse
import com.example.`in`.api.trainer.response.MemberSummaryResponse
import com.example.`in`.common.annotation.AuthenticationUser
import com.example.`in`.common.config.SwaggerConfig
import com.example.`in`.common.resolver.UserInfo
import com.example.`in`.common.response.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class RivalController(
    private val rivalUseCase: RivalUseCase,
    private val memberUseCase: MemberUseCase,
) {
    @GetMapping("/rival/pending")
    @Operation(
        summary = "나에게 라이벌 신청을 한 사용자 확인",
        security = [SecurityRequirement(name = SwaggerConfig.AUTHORIZATION_BEARER_SECURITY_SCHEME_NAME)],
    )
    fun findPendingList(
        @Parameter(hidden = true)
        @AuthenticationUser
        userInfo: UserInfo,
    ): ApiResponse<List<RivalSummaryResponse>> {
        val response = rivalUseCase.getPendingRivalList(userInfo.userId).map {
            RivalSummaryResponse.from(it)
        }
        return ApiResponse(data = response)
    }

    @GetMapping("/rivals")
    @Operation(
        summary = "내 라이벌들 목록 확인",
        security = [SecurityRequirement(name = SwaggerConfig.AUTHORIZATION_BEARER_SECURITY_SCHEME_NAME)],
    )
    fun findMyRivals(
        @Parameter(hidden = true)
        @AuthenticationUser
        userInfo: UserInfo,
    ): ApiResponse<List<RivalSummaryResponse>> {
        val response = rivalUseCase.getMyRivals(userInfo.userId).map {
            RivalSummaryResponse.from(it)
        }
        return ApiResponse(data = response)
    }

    @PostMapping("/rival/accept")
    @Operation(
        summary = "라이벌 승인",
        security = [SecurityRequirement(name = SwaggerConfig.AUTHORIZATION_BEARER_SECURITY_SCHEME_NAME)],
    )
    fun acceptRival(
        @Parameter(hidden = true)
        @AuthenticationUser
        userInfo: UserInfo,
        @RequestBody
        request: UpdateRivalStatusRequest,
    ): ApiResponse<Unit> {
        rivalUseCase.updateRivalStatus(request.toAcceptCommand(userInfo.userId))
        return ApiResponse(data = Unit)
    }

    @PostMapping("/rival/refuse")
    @Operation(
        summary = "라이벌 거절",
        security = [SecurityRequirement(name = SwaggerConfig.AUTHORIZATION_BEARER_SECURITY_SCHEME_NAME)],
    )
    fun refuseRival(
        @Parameter(hidden = true)
        @AuthenticationUser
        userInfo: UserInfo,
        @RequestBody
        request: UpdateRivalStatusRequest,
    ): ApiResponse<Unit> {
        rivalUseCase.updateRivalStatus(request.toRefuseCommand(userInfo.userId))
        return ApiResponse(data = Unit)
    }

    @PostMapping("/rival/request")
    @Operation(
        summary = "라이벌 요청 보내기",
        security = [SecurityRequirement(name = SwaggerConfig.AUTHORIZATION_BEARER_SECURITY_SCHEME_NAME)],
    )
    fun requestRival(
        @Parameter(hidden = true)
        @AuthenticationUser
        userInfo: UserInfo,
        @RequestBody
        request: UpdateRivalStatusRequest,
    ): ApiResponse<Unit> {
        rivalUseCase.updateRivalStatus(request.toRequestCommand(userInfo.userId))
        return ApiResponse(data = Unit)
    }

    @GetMapping("/rival/search")
    @Operation(
        summary = "라이벌 검색",
        security = [SecurityRequirement(name = SwaggerConfig.AUTHORIZATION_BEARER_SECURITY_SCHEME_NAME)],
    )
    fun getMemberByNickname(
        @RequestParam("nickname")
        nickname: String,
    ): ApiResponse<MemberSummaryResponse?> {
        val response = memberUseCase.getMemberByNickname(nickname)?.let {
            MemberSummaryResponse.from(it)
        }
        return ApiResponse(data = response)
    }
}
