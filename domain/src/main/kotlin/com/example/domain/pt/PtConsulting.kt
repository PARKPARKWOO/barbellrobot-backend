package com.example.domain.pt

import java.util.UUID

data class PtConsulting(
    val memberId: UUID,
    val greetingMessage: String,
    val day: List<Day>,
    val warn: String,
    val tip: String,
)

data class Day(
    val target: String,
    val exercise: List<Exercise>,
)

data class Exercise(
    val exerciseId: Long,
    val set: Int,
    val weight: String?,
    val count: Int,
    val advice: String,
)
