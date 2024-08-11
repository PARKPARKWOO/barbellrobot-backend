package com.example.`in`.api.pt.adapter.`in`.response

import com.example.core.pt.model.Day
import com.example.core.pt.model.Exercise
import com.example.core.pt.model.PtConsulting

data class GeneratePtResponse(
    val greetingMessage: String,
    val days: List<DayResponse>,
    val warn: String,
    val tip: String,
) {
    companion object {
        fun from(domain: PtConsulting): GeneratePtResponse = GeneratePtResponse(
            greetingMessage = domain.greetingMessage,
            days = domain.day.map { DayResponse.from(it) },
            warn = domain.warn,
            tip = domain.tip,
        )
    }
}

data class DayResponse(
    val target: String,
    val consultingExercise: List<ConsultingExercise>,
) {
    companion object {
        fun from(domain: Day): DayResponse = DayResponse(
            target = domain.target,
            consultingExercise = domain.exercise.map { ConsultingExercise.from(it) },
        )
    }
}

data class ConsultingExercise(
    val exerciseId: Long,
    val set: Int,
    val weight: String?,
    val count: Int,
    val advice: String,
) {
    companion object {
        fun from(domain: Exercise): ConsultingExercise = ConsultingExercise(
            exerciseId = domain.exerciseId,
            set = domain.set,
            weight = domain.weight,
            count = domain.count,
            advice = domain.advice,
        )
    }
}
