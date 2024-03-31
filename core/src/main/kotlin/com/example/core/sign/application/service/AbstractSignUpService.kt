package com.example.core.sign.application.service

import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.sign.application.port.SignUpUserWithEmailCommand
import com.example.core.sign.application.port.`in`.SignUpUserUseCase
import com.example.core.sign.application.port.out.EmailVerifyPort
import com.example.core.sign.application.port.out.command.VerifyAuthenticationSuccessCommand
import com.example.core.user.application.port.out.UserQueryPort
import org.springframework.transaction.annotation.Transactional

abstract class AbstractSignUpService(
    private val userQueryPort: UserQueryPort,
    private val emailVerifyPort: EmailVerifyPort,
) : SignUpUserUseCase {
    abstract override fun saveUser(command: SignUpUserWithEmailCommand)

    abstract override fun signUpWithKakao()

    @Transactional
    override fun signUpWithEmail(command: SignUpUserWithEmailCommand) {
        verifyNickname(command.nickname)
        verifyEmail(command.toAuthenticationCommand())
        saveUser(command)
    }

    private fun verifyEmail(command: VerifyAuthenticationSuccessCommand) {
        val successCommand = VerifyAuthenticationSuccessCommand(
            email = command.email,
            authenticationString = command.authenticationString,
        )
        emailVerifyPort.verifyAuthenticationSuccess(successCommand)
    }

    private fun verifyNickname(nickname: String) {
        userQueryPort.findByNickname(nickname)?.let {
            throw ServiceException(ErrorCode.DUPLICATE_NICKNAME)
        }
    }
}
