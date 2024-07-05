package com.example.api.pt.adapter.`in`

import com.example.api.common.annotation.AuthenticationUser
import com.example.api.common.annotation.RateLimit
import com.example.api.common.config.SwaggerConfig
import com.example.api.common.resolver.UserInfo
import com.example.api.common.response.ApiResponse
import com.example.api.pt.adapter.`in`.request.GeneratePtRequest
import com.example.api.pt.adapter.`in`.response.GeneratePtResponse
import com.example.api.pt.adapter.`in`.response.HasPtResponse
import com.example.core.pt.application.port.`in`.GeneratePtUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.TimeUnit

@RestController
@RequestMapping("/api/v1/pt")
class PtController(
    private val ptUseCase: GeneratePtUseCase,
) {
    // TODO: token 사용량 계산하여 Quota 다시 설정
    /**
     * 1회 요청당 약 2000~3000 Token 사용 (운동 입력 안했을때)
     * gemini flash 기준 분당 15 요청, 100만 토큰 허용
     * draft 하게 계산 했을때 token quota 를 초과하진 않음
     * 추후 Batch 로 토큰 계산 및 quota 설정 필요
     */
    @PostMapping
    @Operation(
        summary = "이번주 pt 받기",
        security = [SecurityRequirement(name = SwaggerConfig.AUTHORIZATION_BEARER_SECURITY_SCHEME_NAME)],
    )
    @RateLimit(quota = 1, timeUnit = TimeUnit.MINUTES, refillInterval = 1, refillTokens = 1)
    fun generatePt(
        @AuthenticationUser
        @Parameter(hidden = true)
        userInfo: UserInfo,
        @RequestBody
        request: GeneratePtRequest,
    ): ApiResponse<GeneratePtResponse> {
        val response = GeneratePtResponse.from(ptUseCase.generatePt(request.toCommand(userInfo.userId)).consulting)
        return ApiResponse(data = response)
    }

    @GetMapping
    @Operation(
        summary = "바로 리턴",
        security = [SecurityRequirement(name = SwaggerConfig.AUTHORIZATION_BEARER_SECURITY_SCHEME_NAME)],
    )
    fun getPt(
        @AuthenticationUser
        @Parameter(hidden = true)
        userInfo: UserInfo,
    ): ApiResponse<GeneratePtResponse?> {
        val response = ptUseCase.getPt(userInfo.userId)?.let {
            GeneratePtResponse.from(it.consulting)
        }
        return ApiResponse(data = response)
    }

    @GetMapping("/check")
    @Operation(
        summary = "pt 있는지 확인",
        security = [SecurityRequirement(name = SwaggerConfig.AUTHORIZATION_BEARER_SECURITY_SCHEME_NAME)],
    )
    fun checkPt(
        @AuthenticationUser
        @Parameter(hidden = true)
        userInfo: UserInfo,
    ): ApiResponse<HasPtResponse> {
        val response = ptUseCase.getPt(userInfo.userId)?.let { HasPtResponse(true) } ?: HasPtResponse(false)
        return ApiResponse(data = response)
    }
}
