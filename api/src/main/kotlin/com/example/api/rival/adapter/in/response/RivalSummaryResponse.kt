package com.example.api.rival.adapter.`in`.response

import com.example.core.rival.dto.RivalSummaryDto
import com.example.core.rival.model.RivalStatus
import java.util.UUID

data class RivalSummaryResponse(
    val memberId: UUID,
    val profile: String?,
    val nickname: String,
    val rivalStatus: RivalStatus,
) {
    companion object {
        fun from(dto: RivalSummaryDto): RivalSummaryResponse = RivalSummaryResponse(
            memberId = dto.memberId,
            nickname = dto.nickname,
            profile = dto.profile,
            rivalStatus = dto.rivalStatus,
        )
    }
}
