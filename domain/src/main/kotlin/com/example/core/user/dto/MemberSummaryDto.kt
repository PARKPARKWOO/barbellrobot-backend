package com.example.core.user.dto

import java.util.UUID

data class MemberSummaryDto(
    val id: UUID,
    val nickname: String,
    val profile: String?,
)
