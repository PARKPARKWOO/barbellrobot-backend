package com.example.api.exercise.adapter.`in`

import com.example.api.common.annotation.PublicEndPoint
import com.example.api.common.response.ApiResponse
import com.example.api.exercise.adapter.`in`.request.CreateExerciseItemRequest
import com.example.api.exercise.adapter.`in`.response.ExerciseItemDetailResponse
import com.example.core.exercise.application.port.`in`.ExerciseItemUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
@Tag(
    name = "운동 아이템",
    description = """
        예시) 벤치 프레스, 사이드 레터럴 레이즈 등..
    """,
)
class ExerciseItemController(
    private val exerciseItemUseCase: ExerciseItemUseCase,
) {
    @PostMapping("/item")
    @PublicEndPoint
    @Operation(
        summary = "운동 을 추가합니다. ex) 벤치프레스",
    )
    fun createItem(
        @RequestBody
        request: CreateExerciseItemRequest,
    ): ApiResponse<Unit> {
        exerciseItemUseCase.saveExerciseItem(request.toCommand())
        return ApiResponse(data = Unit)
    }

    @GetMapping("/item/{id}")
    @Operation(
        summary = "운동 아이템 조회",
        description = """
            벤치 프레스, 풀업 등
            운동아이템을 어느 부위 운동인지,
            어떤 목적인지(다이어트 등) 묶어 조회
        """,
    )
    @PublicEndPoint
    fun findItem(
        @PathVariable("id")
        id: Long,
    ): ApiResponse<ExerciseItemDetailResponse> {
        val queryItem = exerciseItemUseCase.queryItem(id)
        val response = ExerciseItemDetailResponse.from(queryItem)
        return ApiResponse(data = response)
    }

    @DeleteMapping("/item/{id}")
    @PublicEndPoint
    fun deleteItem(
        @PathVariable("id") id: Long,
    ): ApiResponse<Unit> {
        exerciseItemUseCase.deleteItem(id)
        return ApiResponse(data = Unit)
    }

    @GetMapping("/items")
    @PublicEndPoint
    @Operation(
        summary = "운동 목록 filter",
    )
    fun findItems(): ApiResponse<List<ExerciseItemDetailResponse>> {
        return ApiResponse(
            data = exerciseItemUseCase.findAllItemsQuery().map {
                ExerciseItemDetailResponse.from(it)
            },
        )
    }
}
