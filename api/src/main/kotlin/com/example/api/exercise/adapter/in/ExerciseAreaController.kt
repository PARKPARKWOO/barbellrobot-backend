package com.example.api.exercise.adapter.`in`

import com.example.api.exercise.adapter.`in`.request.CreateExerciseAreaRequest
import com.example.api.response.ApiResponse
import com.example.api.common.annotation.PublicEndPoint
import com.example.core.exercise.application.`in`.ExerciseAreaUseCase
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/area")
class ExerciseAreaController(
    private val exerciseAreaUseCase: ExerciseAreaUseCase,
) {
    @PostMapping
    @PublicEndPoint
    fun createArea(
        @RequestBody
        request: CreateExerciseAreaRequest,
    ): ApiResponse<Unit> {
        exerciseAreaUseCase.saveExerciseArea(request.toCommand())
        return ApiResponse(data = Unit)
    }

    @DeleteMapping("/{id}")
    @PublicEndPoint
    fun deleteArea(
        @PathVariable("id") id: Long,
    ): ApiResponse<Unit> {
        exerciseAreaUseCase.deleteExerciseArea(id)
        return ApiResponse(data = Unit)
    }
}
