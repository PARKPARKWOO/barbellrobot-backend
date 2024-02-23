package com.example.api.exercise.adapter.`in`

import com.example.api.common.annotation.PublicEndPoint
import com.example.api.exercise.adapter.`in`.request.CreateExerciseItemRequest
import com.example.api.exercise.adapter.`in`.response.ExerciseItemResponse
import com.example.api.common.response.ApiResponse
import com.example.core.exercise.application.`in`.ExerciseItemUseCase
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/item")
class ExerciseItemController(
    private val exerciseItemUseCase: ExerciseItemUseCase,
) {
    @PostMapping
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

    @GetMapping("/{id}")
    @PublicEndPoint
    fun findItem(
        @PathVariable("id")
        id: Long,
    ): ApiResponse<ExerciseItemResponse> {
        val queryItem = exerciseItemUseCase.queryItem(id)
        val response = ExerciseItemResponse.of(
            item = queryItem.item,
            areas = queryItem.areas,
            goals = queryItem.goals,
        )
        return ApiResponse(data = response)
    }

    @DeleteMapping("/{id}")
    @PublicEndPoint
    fun deleteItem(
        @PathVariable("id") id: Long,
    ): ApiResponse<Unit> {
        exerciseItemUseCase.deleteItem(id)
        return ApiResponse(data = Unit)
    }
}
