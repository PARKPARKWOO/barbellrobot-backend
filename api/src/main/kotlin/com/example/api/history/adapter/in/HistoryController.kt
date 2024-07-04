package com.example.api.history.adapter.`in`

import com.example.api.common.annotation.AuthenticationUser
import com.example.api.common.annotation.RateLimit
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
import com.example.core.history.application.port.query.GetHistoryQuery
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate
import java.util.concurrent.TimeUnit

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
    @RateLimit(quota = 1, timeUnit = TimeUnit.SECONDS, refillInterval = 3, refillTokens = 1)
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
    @RateLimit(quota = 1, timeUnit = TimeUnit.SECONDS, refillInterval = 3, refillTokens = 1)
    fun exerciseToday(
        @AuthenticationUser
        @Parameter(hidden = true)
        userInfo: UserInfo,
        @RequestBody
        request: CompleteTodayExerciseRequest,
    ): ApiResponse<Unit> {
        generateHistoryUseCase.exerciseToday(request.toCommand())
        return ApiResponse(data = Unit)
    }

    @PostMapping(path = ["/diet"], consumes = ["multipart/form-data", "application/json"])
    @Operation(
        summary = "오늘 식단을 추가합니다.",
        description = "아침, 점심, 저녁 메뉴, 사진 을 추가할 수 있습니다.",
        security = [SecurityRequirement(name = SwaggerConfig.AUTHORIZATION_BEARER_SECURITY_SCHEME_NAME)],
    )
    @RateLimit(quota = 1, timeUnit = TimeUnit.SECONDS, refillInterval = 3, refillTokens = 1)
    suspend fun uploadDiet(
        @Parameter(hidden = true) @AuthenticationUser
        userInfo: UserInfo,
        @RequestPart("request") request: AddDietRequest,
        @RequestPart("images", required = false) images: List<MultipartFile>?,
    ): ApiResponse<Unit> {
        historyFacadeUseCase.addDietToday(
            request.toCommand(
                userId = userInfo.userId,
                images = images,
            ),
        )
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
        @RequestParam(name = "thisWeek", required = false)
        thisWeek: Boolean = true,
    ): ApiResponse<List<HistoryResponse>> {
        val date = if (thisWeek) {
            LocalDate.now()
        } else {
            LocalDate.now().minusWeeks(1)
        }
        val query = GetHistoryQuery(
            userId = userInfo.userId,
            localDate = date,
        )
        val response = historyQueryUseCase.getHistoryFromWeek(query).map { historyDto ->
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
        @RequestParam(name = "month", required = false)
        month: Int?,
        @RequestParam(name = "year", required = false)
        year: Int?,
    ): ApiResponse<List<HistoryResponse>> {
        val currentDate = LocalDate.now()
        val queryYear = year ?: currentDate.year
        val queryMonth = month ?: currentDate.monthValue
        val localDate = LocalDate.of(queryYear, queryMonth, 1)
        val query = GetHistoryQuery(
            userId = userInfo.userId,
            localDate = localDate,
        )
        val response = historyQueryUseCase.getHistoryFromMonth(query)?.map { historyDto ->
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
