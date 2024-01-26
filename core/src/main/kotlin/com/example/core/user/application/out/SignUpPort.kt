package com.example.core.user.application.out

interface SignUpPort {
    fun findNicknameUser(nickName: String): Boolean
}
