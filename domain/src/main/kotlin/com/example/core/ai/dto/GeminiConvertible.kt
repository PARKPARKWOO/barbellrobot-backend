package com.example.core.ai.dto

import java.util.UUID

interface GeminiConvertible<out T> {
    fun toDomain(memberId: UUID): T
}
