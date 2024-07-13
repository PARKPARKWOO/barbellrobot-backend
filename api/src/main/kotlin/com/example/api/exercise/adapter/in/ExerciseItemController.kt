package com.example.api.exercise.adapter.`in`

import com.example.api.common.annotation.PublicEndPoint
import com.example.api.common.response.ApiResponse
import com.example.api.exercise.adapter.`in`.request.AddYoutubeRequest
import com.example.api.exercise.adapter.`in`.response.ExerciseItemDetailResponse
import com.example.core.exercise.port.command.SaveExerciseItemCommand
import com.example.core.exercise.port.`in`.ExerciseItemUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

/**
 * 조회 제외 admin 으로 변경
 */

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
    @PostMapping(path = ["/item"], consumes = ["multipart/form-data"])
    @PublicEndPoint
    @Operation(
        summary = "운동 을 추가합니다. ex) 벤치프레스",
    )
    suspend fun createItem(
        @RequestParam("exerciseName")
        exerciseName: String,
        @RequestParam(name = "video", required = false)
        video: List<MultipartFile>?,
        @RequestParam(name = "image", required = false)
        image: List<MultipartFile>?,
        @RequestParam(name = "exerciseAreas")
        exerciseAreas: MutableList<Long>,
        @RequestParam(name = "exerciseGoals")
        exerciseGoals: MutableList<Long>,
    ): ApiResponse<Unit> {
        val command = SaveExerciseItemCommand(
            exerciseName = exerciseName,
            video = video,
            image = image,
            exerciseAreas = exerciseAreas,
            exerciseGoals = exerciseGoals,
        )
        exerciseItemUseCase.saveExerciseItem(command)
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
    fun findItems(
        @RequestParam("itemIds", required = false)
        itemIds: List<Long>?,
    ): ApiResponse<List<ExerciseItemDetailResponse>> {
        val response = exerciseItemUseCase.findAllItemsQuery(itemIds).map {
            ExerciseItemDetailResponse.from(it)
        }
        println(response.toString())
        return ApiResponse(
            data = response,
        )
    }

    @PostMapping("/item/youtube")
    @PublicEndPoint
    @Operation(
        summary = "Youtube 영상 업로드 하기",
    )
    fun addYoutube(
        @RequestBody
        request: AddYoutubeRequest,
    ): ApiResponse<Unit> {
        exerciseItemUseCase.addYoutubeLink(request.toCommand())
        return ApiResponse(data = Unit)
    }

    @DeleteMapping("/item/youtube/{id}")
    @PublicEndPoint
    @Operation(
        summary = "Youtube 영상 삭제",
    )
    fun deleteYoutube(@PathVariable id: Long): ApiResponse<Unit> {
        exerciseItemUseCase.deleteItem(id)
        return ApiResponse(data = Unit)
    }
}
