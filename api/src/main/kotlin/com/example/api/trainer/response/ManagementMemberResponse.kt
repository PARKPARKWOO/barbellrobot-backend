package com.example.api.trainer.response

import com.example.domain.user.MemberSummary
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
        @JvmStatic
        fun from(domain: MemberSummary) = MemberSummaryResponse(
            memberId = domain.id,
            nickname = domain.nickname,
            profile = domain.profile,
        )
    }
}
