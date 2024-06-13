package com.example.api.pt.adapter.`in`

import com.example.api.common.annotation.AuthenticationUser
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

@RestController
@RequestMapping("/api/v1/pt")
class PtController(
    private val ptUseCase: GeneratePtUseCase,
) {
    @PostMapping
    @Operation(
        summary = "이번주 pt 받기",
        security = [SecurityRequirement(name = SwaggerConfig.AUTHORIZATION_BEARER_SECURITY_SCHEME_NAME)],
    )
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
