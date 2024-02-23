package com.example.core.user.application.out

import com.example.core.user.UserEntity

interface UserQueryPort {
    fun findByNickname(nickname: String): UserEntity?
}
