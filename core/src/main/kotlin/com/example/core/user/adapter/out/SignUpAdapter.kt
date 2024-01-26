package com.example.core.user.adapter.out

import com.example.core.user.adapter.out.persistence.UserQueryRepository
import com.example.core.user.application.out.SignUpPort
import org.springframework.stereotype.Component

@Component
class SignUpAdapter(
    private val userQueryRepository: UserQueryRepository,
): SignUpPort {
    override fun findNicknameUser(nickName: String): Boolean {
        return userQueryRepository.findNickname(nickName)
    }

}