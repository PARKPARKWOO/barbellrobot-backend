package com.example.core.user

import java.util.UUID

interface UserHealthDetail {
    val userId: UUID

    fun toModel(): UserHealthDetail
}
