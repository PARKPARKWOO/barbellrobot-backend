package com.example.core.user.model.interfaces

import com.example.core.user.model.Role

interface UserEntity {
    val role: Role

    fun toUserEntity(): UserEntity

    fun uploadProfile(uri: String)
}
