package com.example.core.pt.model

import com.example.core.user.model.Member
import java.time.LocalDateTime

data class AiPt(
    val consulting: PtConsulting,
    val createdAt: LocalDateTime,
    val member: Member,
)
