package com.example.`in`.api.trainer.response

import com.example.core.user.dto.MemberSummaryDto
import java.util.UUID

data class ManagementMemberResponse(
    val memberSummaryList: List<MemberSummaryResponse>,
)

data class MemberSummaryResponse(
    val memberId: UUID,
    val nickname: String,
    val profile: String?,
) {
    companion object {
        fun from(dto: MemberSummaryDto) = MemberSummaryResponse(
            memberId = dto.id,
            nickname = dto.nickname,
            profile = dto.profile,
        )
    }
}
