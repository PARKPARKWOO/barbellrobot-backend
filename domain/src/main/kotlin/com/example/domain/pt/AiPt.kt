package com.example.domain.pt

import com.example.domain.user.Member
import java.time.LocalDateTime

data class AiPt(
    val content: String,
    val createdAt: LocalDateTime,
    val member: Member,
)
