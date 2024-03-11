package com.example.core.user

import com.example.domain.user.Role

interface UserEntity {
    val role: Role
    fun toUserEntity(): UserEntity
}
