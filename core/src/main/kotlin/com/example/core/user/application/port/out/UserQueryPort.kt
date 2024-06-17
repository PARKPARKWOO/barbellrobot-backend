package com.example.core.user.application.port.out

import com.example.core.user.UserEntity
import com.example.core.user.UserHealthDetail
import java.util.UUID

interface UserQueryPort {
    fun findByNickname(nickname: String): UserHealthDetail?

    fun findById(userId: UUID): UserEntity?

    fun findByEmail(email: String): UserEntity?
}
