package com.example.api.exercise.adapter.`in`

import com.example.api.common.annotation.PublicEndPoint
import com.example.api.common.response.ApiResponse
import com.example.api.exercise.adapter.`in`.request.CreateExerciseAreaRequest
import com.example.api.exercise.adapter.`in`.response.ExerciseAreaResponse
import com.example.core.exercise.application.`in`.ExerciseAreaUseCase
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
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

    @PublicEndPoint
    @GetMapping("/test")
    fun test() {
        println(Thread.currentThread().id)
    }

    @GetMapping("/all")
    @PublicEndPoint
    fun getAllArea(): ApiResponse<List<ExerciseAreaResponse>> {
        val response = exerciseAreaUseCase.getAllExerciseArea().map { area ->
            ExerciseAreaResponse.from(area)
        }
        return ApiResponse(data = response)
    }

    @GetMapping("/{id}")
    @PublicEndPoint
    fun getArea(
        @PathVariable("id") id: Long,
    ): ApiResponse<ExerciseAreaResponse> {
        val response = ExerciseAreaResponse.from(exerciseAreaUseCase.getExerciseArea(id))
        return ApiResponse(data = response)
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
