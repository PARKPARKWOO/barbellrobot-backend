package com.example.api.history.adapter.`in`

import com.example.api.common.annotation.AuthenticationUser
import com.example.api.common.config.SwaggerConfig
import com.example.api.common.resolver.UserInfo
import com.example.api.common.response.ApiResponse
import com.example.api.history.adapter.`in`.request.AddDietRequest
import com.example.api.history.adapter.`in`.request.CompleteTodayExerciseRequest
import com.example.api.history.adapter.`in`.response.AttendanceResponse
import com.example.api.history.adapter.`in`.response.ExerciseHistoryResponse
import com.example.api.history.adapter.`in`.response.HistoryResponse
import com.example.api.history.adapter.`in`.response.UserHistoryResponse
import com.example.common.log.Log
import com.example.core.history.application.port.`in`.GenerateHistoryUseCase
import com.example.core.history.application.port.`in`.HistoryFacadeUseCase
import com.example.core.history.application.port.`in`.HistoryQueryUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/history")
@Suppress("UnusedParameter")
class HistoryController(
    private val generateHistoryUseCase: GenerateHistoryUseCase,
    private val historyFacadeUseCase: HistoryFacadeUseCase,
    private val historyQueryUseCase: HistoryQueryUseCase,
) : Log {
    @PostMapping("/attendance")
    @Operation(
        summary = "출석 api",
        security = [SecurityRequirement(name = SwaggerConfig.AUTHORIZATION_BEARER_SECURITY_SCHEME_NAME)],
    )
    fun attendance(
        @Parameter(hidden = true) @AuthenticationUser
        userInfo: UserInfo,
    ): ApiResponse<AttendanceResponse> {
        val attendanceToday = generateHistoryUseCase.attendanceToday(userInfo.userId)
        val response = AttendanceResponse(
            todayHistoryId = attendanceToday,
        )
        return ApiResponse(data = response)
    }

    @PostMapping("/exercise")
    @Operation(
        summary = "오늘 운동을 기록 합니다",
        description = "",
        security = [SecurityRequirement(name = SwaggerConfig.AUTHORIZATION_BEARER_SECURITY_SCHEME_NAME)],
    )
    fun exerciseToday(
        @RequestBody
        request: CompleteTodayExerciseRequest,
    ): ApiResponse<Unit> {
        generateHistoryUseCase.exerciseToday(request.toCommand())
        return ApiResponse(data = Unit)
    }

    @PatchMapping("/diet")
    @Operation(
        summary = "오늘 식단을 추가합니다.",
        description = "아침, 점심, 저녁 메뉴, 사진 을 추가할 수 있습니다.",
        security = [SecurityRequirement(name = SwaggerConfig.AUTHORIZATION_BEARER_SECURITY_SCHEME_NAME)],
    )
    suspend fun uploadDiet(
        @Parameter(hidden = true) @AuthenticationUser
        userInfo: UserInfo,
        @ModelAttribute
        request: AddDietRequest,
    ): ApiResponse<Unit> {
        historyFacadeUseCase.addDietToday(request.toCommand(userInfo.userId))
        return ApiResponse(data = Unit)
    }
    // 같은 Month 조회 API

    // 같은 Week 조회 API
    @GetMapping("/week")
    @Operation(
        summary = "이번 주 출석한 기록 api",
        security = [SecurityRequirement(name = SwaggerConfig.AUTHORIZATION_BEARER_SECURITY_SCHEME_NAME)],
    )
    fun getHistoryFromWeek(
        @Parameter(hidden = true) @AuthenticationUser
        userInfo: UserInfo,
    ): ApiResponse<List<HistoryResponse>> {
        val response = historyQueryUseCase.getHistoryFromWeek(userInfo.userId)?.map { historyDto ->
            HistoryResponse(
                userHistoryResponse = UserHistoryResponse.from(historyDto.userHistoryResponseDto),
                exerciseHistoryResponse = historyDto.exerciseHistoryResponseDto?.map {
                    ExerciseHistoryResponse.from(it)
                },
            )
        }
        return ApiResponse(data = response)
    }

    @GetMapping("/month")
    @Operation(
        summary = "이번 달 출석한 기록 api",
        security = [SecurityRequirement(name = SwaggerConfig.AUTHORIZATION_BEARER_SECURITY_SCHEME_NAME)],
    )
    fun getHistoryFromMonth(
        @Parameter(hidden = true) @AuthenticationUser
        userInfo: UserInfo,
    ): ApiResponse<List<HistoryResponse>> {
        val response = historyQueryUseCase.getHistoryFromWeek(userInfo.userId)?.map { historyDto ->
            HistoryResponse(
                userHistoryResponse = UserHistoryResponse.from(historyDto.userHistoryResponseDto),
                exerciseHistoryResponse = historyDto.exerciseHistoryResponseDto?.map {
                    ExerciseHistoryResponse.from(it)
                },
            )
        }
        return ApiResponse(data = response)
    }

    @GetMapping("/today")
    @Operation(
        summary = "오늘 운동 기록",
        security = [SecurityRequirement(name = SwaggerConfig.AUTHORIZATION_BEARER_SECURITY_SCHEME_NAME)],
    )
    fun getHistoryFromToday(
        @Parameter(hidden = true) @AuthenticationUser
        userInfo: UserInfo,
    ): ApiResponse<HistoryResponse> {
        val response = historyQueryUseCase.getHistoryFromToday(userInfo.userId)?.let {
            HistoryResponse(
                userHistoryResponse = UserHistoryResponse.from(it.userHistoryResponseDto),
                exerciseHistoryResponse = it.exerciseHistoryResponseDto?.map { exerciseHistoryResponseDto ->
                    ExerciseHistoryResponse.from(exerciseHistoryResponseDto)
                },
            )
        }
        return ApiResponse(data = response)
    }
}
