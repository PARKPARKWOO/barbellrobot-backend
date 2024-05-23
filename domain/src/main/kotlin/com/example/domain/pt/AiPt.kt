package com.example.domain.pt

import com.example.domain.user.Member
import java.time.LocalDateTime

data class AiPt(
    val consulting: PtConsulting,
    val createdAt: LocalDateTime,
    val member: Member,
)
