package com.example.api.member.response

import com.example.core.user.dto.MemberAndGoalQueryDto
import com.example.core.user.model.Role

data class GetMemberMyPageResponse(
    val profile: String?,
    val role: Role,
    val memberInfoResponse: MemberInfoResponse?,
    val memberGoals: List<String>,
) {
    companion object {
        fun from(query: MemberAndGoalQueryDto): GetMemberMyPageResponse = GetMemberMyPageResponse(
            profile = query.memberDetailQueryDto?.profile,
            role = query.memberDetailQueryDto!!.role,
            memberInfoResponse = MemberInfoResponse.from(query.memberDetailQueryDto!!.memberInfoQueryDto!!),
            memberGoals = query.memberGoal,
        )
    }
}
