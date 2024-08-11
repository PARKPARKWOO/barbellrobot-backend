package com.example.`in`.api.exercise.adapter.`in`

import com.example.application.common.log.logger
import com.example.core.exercise.port.`in`.ExerciseAreaUseCase
import com.example.`in`.api.exercise.adapter.`in`.request.CreateExerciseAreaRequest
import com.example.`in`.api.exercise.adapter.`in`.response.ExerciseAreaResponse
import com.example.`in`.common.annotation.PublicEndPoint
import com.example.`in`.common.response.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.CompletableFuture

@RestController
@RequestMapping("/api/v1/area")
@Tag(name = "운동 부위")
class ExerciseAreaController(
    private val exerciseAreaUseCase: ExerciseAreaUseCase,
) {
    private val log = logger()
    @PostMapping
    @Operation(
        summary = "운동 부위 추가",
        description = "가슴, 어깨 등...",
    )
    @PublicEndPoint
    fun createArea(
        @RequestBody
        request: CreateExerciseAreaRequest,
    ): ApiResponse<Unit> {
        exerciseAreaUseCase.saveExerciseArea(request.toCommand())
        return ApiResponse(data = Unit)
    }

    @GetMapping("/all")
    @Operation(
        summary = "현재 저장하고있는 부위 호출",
    )
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
