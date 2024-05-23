package com.example.core.ai.application.service

import com.example.core.ai.application.dto.ExerciseList
import com.fasterxml.jackson.annotation.JsonClassDescription
import com.google.common.base.Function

class GeneratePtService : Function<GeneratePtService.GeneratePtRequestDto, GeneratePtService.GeneratePtResponseDto> {
    @JsonClassDescription("Get a list of exercises.")
    data class GeneratePtRequestDto(
        val exerciseList: List<ExerciseList>? = null,
    )

    data class GeneratePtResponseDto(
        val greetingMessage: String? = null,
        val day: List<Day>? = null,
        val warn: String? = null,
        val tip: String? = null,
    )

    override fun apply(input: GeneratePtRequestDto?): GeneratePtResponseDto {
        return GeneratePtResponseDto(
            greetingMessage = "",
            day = listOf(
                Day(
                    target = "",
                    exercise = listOf(
                        Exercise(
                            exerciseId = 0L,
                            set = 0,
                            weight = "",
                            count = 0,
                            "",
                        ),
                    ),
                ),
            ),
        )
    }

    companion object {
        const val FUNCTION_NAME = "GeneratePtService"
    }
}

data class Day(
    val target: String?,
    val exercise: List<Exercise>?,
)

data class Exercise(
    val exerciseId: Long?,
    val set: Int?,
    val weight: String?,
    val count: Int?,
    val advice: String?,
)
