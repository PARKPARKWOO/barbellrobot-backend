package com.example.core.user.model.interfaces

import java.util.UUID

interface UserHealthDetail {
    val userId: UUID

    fun toModel(): UserHealthDetail
}
