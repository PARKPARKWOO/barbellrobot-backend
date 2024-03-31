package com.example.domain.user

import java.util.UUID

data class MemberSummary(
    val id: UUID,
    val nickname: String,
    val profile: String?,
)
