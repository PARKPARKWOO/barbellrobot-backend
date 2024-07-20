package com.example.api.trainer.response

import com.example.core.user.dto.MemberSummaryDto

data class ManagementMemberResponse(
    val memberSummaryList: List<MemberSummaryResponse>,
)

data class MemberSummaryResponse(
    val nickname: String,
    val profile: String?,
) {
    companion object {
        @JvmStatic
        fun from(dto: MemberSummaryDto) = MemberSummaryResponse(
            nickname = dto.nickname,
            profile = dto.profile,
        )
    }
}
