package com.example.core.rival.dto

import com.example.core.rival.model.RivalStatus
import java.util.UUID

data class RivalSummaryDto(
    val memberId: UUID,
    val nickname: String,
    val profile: String?,
    val rivalStatus: RivalStatus,
)
