package com.example.core.ai.application.service

import com.example.core.ai.application.dto.ExerciseList
import com.example.domain.pt.PtConsulting
import com.fasterxml.jackson.annotation.JsonClassDescription
import com.google.common.base.Function
import java.util.UUID

typealias DayModel = com.example.domain.pt.Day
typealias ExerciseModel = com.example.domain.pt.Exercise

class GeneratePtService : Function<GeneratePtService.GeneratePtRequestDto, GeneratePtService.GeneratePtResponseDto> {
    @JsonClassDescription("Get a list of exercises.")
    data class GeneratePtRequestDto(
        val exerciseList: List<ExerciseList>? = null,
    )

    data class GeneratePtResponseDto(
        val greetingMessage: String = "",
        val day: List<Day> = emptyList(),
        val warn: String = "",
        val tip: String = "",
    ) {
        fun toDomain(memberId: UUID): PtConsulting = PtConsulting(
            memberId = memberId,
            greetingMessage = greetingMessage,
            day = day.map { it.toDomain() },
            warn = warn,
            tip = tip,
        )
    }

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
    val target: String,
    val exercise: List<Exercise>,
) {
    fun toDomain(): DayModel = DayModel(
        target = target,
        exercise = exercise.map { it.toDomain() },
    )
}

data class Exercise(
    val exerciseId: Long = 0,
    val set: Int = 0,
    val weight: String? = "",
    val count: Int = 0,
    val advice: String = "",
) {
    fun toDomain(): ExerciseModel = ExerciseModel(
        exerciseId = exerciseId,
        set = set,
        weight = weight,
        count = count,
        advice = advice,
    )
}
