package com.example.core.user.application.port.out

import com.example.core.user.UserEntity
import java.util.UUID

interface UserQueryPort {
    fun findByNickname(nickname: String): UserEntity?

    fun findById(userId: UUID): UserEntity?
}
