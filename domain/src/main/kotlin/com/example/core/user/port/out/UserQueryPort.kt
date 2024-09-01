package com.example.core.user.port.out

import com.example.core.user.model.interfaces.UserEntity
import com.example.core.user.model.interfaces.UserHealthDetail
import java.util.UUID

interface UserQueryPort {
    fun findByNickname(nickname: String): UserHealthDetail?

    fun findById(userId: UUID): UserEntity?

    fun findByEmail(email: String): UserEntity?
}
