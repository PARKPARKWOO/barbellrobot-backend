package com.example.domain.history

import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

data class UserHistory(
    val id: UUID,
    val userId: UUID,
    val breakfast: Breakfast,
    val lunch: Lunch,
    val dinner: Dinner,
    val attendance: Boolean = true,
    val createdAt: LocalDate,
    val todayImages: List<String>,
    val todayVideo: List<String>,
    val updatedAt: LocalDateTime,
)

data class Breakfast(
    val foods: List<String>,
    val images: List<String>,
)

data class Lunch(
    val foods: List<String>,
    val images: List<String>,
)

data class Dinner(
    val foods: List<String>,
    val images: List<String>,
)
