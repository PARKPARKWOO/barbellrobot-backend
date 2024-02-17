package com.example.api.exercise.adapter.`in`

import com.example.api.exercise.adapter.`in`.request.CreateExerciseGoalRequest
import com.example.api.response.ApiResponse
import com.example.api.common.annotation.PublicEndPoint
import com.example.core.exercise.application.`in`.ExerciseGoalUseCase
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/goal")
class ExerciseGoalController(
    private val exerciseGoalUseCase: ExerciseGoalUseCase,
) {
    @PostMapping
    @PublicEndPoint
    fun createGoal(
        @RequestBody
        request: CreateExerciseGoalRequest,
    ): ApiResponse<Unit> {
        exerciseGoalUseCase.saveExerciseGoal(request.toCommand())
        return ApiResponse(data = Unit)
    }

    @DeleteMapping("/{id}")
    @PublicEndPoint
    fun deleteGoal(
        @PathVariable("id")
        id: Long,
    ): ApiResponse<Unit> {
        exerciseGoalUseCase.deleteExerciseGoal(id)
        return ApiResponse(data = Unit)
    }
}
