package com.example.api.history.adapter.`in`

import com.example.api.common.annotation.AuthenticationUser
import com.example.api.common.config.SwaggerConfig
import com.example.api.common.resolver.UserInfo
import com.example.api.common.response.ApiResponse
import com.example.core.history.application.port.`in`.GenerateHistoryUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/history")
class HistoryController(
    private val generateHistoryUseCase: GenerateHistoryUseCase,
) {
    @PostMapping("/attendance")
    @Operation(
        summary = "출석 api",
        security = [SecurityRequirement(name = SwaggerConfig.AUTHORIZATION_BEARER_SECURITY_SCHEME_NAME)],
    )
    fun attendance(
        @Parameter(hidden = true) @AuthenticationUser
        userInfo: UserInfo,
    ): ApiResponse<Unit> {
        generateHistoryUseCase.attendanceToday(userInfo.userId)
        return ApiResponse(data = Unit)
    }
}
