package com.example.`in`.api.exercise.adapter.`in`

import com.example.`in`.common.annotation.PublicEndPoint
import com.example.`in`.common.response.ApiResponse
import com.example.core.exercise.port.`in`.ExerciseGoalUseCase
import com.example.`in`.api.exercise.adapter.`in`.request.CreateExerciseGoalRequest
import com.example.`in`.api.exercise.adapter.`in`.response.ExerciseGoalResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/goal")
@Tag(
    name = "운동 목표",
    description = """
    user 가 목표로 하는 것들
    예시) 다이어트, 근성장
""",
)
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

    @GetMapping("/{id}")
    @PublicEndPoint
    fun getGoal(
        @PathVariable("id")
        id: Long,
    ): ApiResponse<ExerciseGoalResponse> {
        val response = ExerciseGoalResponse.from(exerciseGoalUseCase.getGoal(id))
        return ApiResponse(data = response)
    }

    @GetMapping("/all")
    @PublicEndPoint
    fun getAllGoals(): ApiResponse<List<ExerciseGoalResponse>> {
        val response = exerciseGoalUseCase.getAllGoals().map { goal ->
            ExerciseGoalResponse.from(goal)
        }
        return ApiResponse(data = response)
    }
}
