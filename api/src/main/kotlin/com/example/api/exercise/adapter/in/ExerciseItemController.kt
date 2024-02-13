package com.example.api.exercise.adapter.`in`

import com.example.api.exercise.adapter.`in`.request.CreateExerciseItemRequest
import com.example.api.response.ApiResponse
import com.example.core.exercise.application.`in`.ExerciseItemUseCase
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
    fun createItem(
        @RequestBody
        request: CreateExerciseItemRequest,
    ): ApiResponse<Unit> {
        exerciseItemUseCase.saveExerciseItem(request.toCommand())
        return ApiResponse(data = Unit)
    }
}
