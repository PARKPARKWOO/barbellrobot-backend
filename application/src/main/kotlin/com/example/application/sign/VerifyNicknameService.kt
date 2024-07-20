package com.example.application.sign

import com.example.core.sign.port.`in`.VerifyNicknameUseCase
import com.example.core.user.port.out.UserQueryPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class VerifyNicknameService(
    private val userQueryPort: UserQueryPort,
) : VerifyNicknameUseCase {
    @Transactional(readOnly = true)
    override fun verifyNickname(nickname: String): Boolean {
        return userQueryPort.findByNickname(nickname)?.let {
            false
        } ?: true
    }
}
