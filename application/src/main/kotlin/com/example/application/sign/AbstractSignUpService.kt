package com.example.application.sign

import com.example.core.common.annotation.DistributedLock
import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.mail.application.port.out.EmailVerifyPort
import com.example.core.mail.application.port.command.VerifyAuthenticationSuccessCommand
import com.example.core.sign.port.`in`.command.SignUpUserWithEmailCommand
import com.example.core.sign.port.`in`.SignUpUserUseCase
import com.example.core.user.port.out.UserQueryPort

abstract class AbstractSignUpService(
    private val userQueryPort: UserQueryPort,
    private val emailVerifyPort: EmailVerifyPort,
) : SignUpUserUseCase {
    abstract override fun saveUser(command: SignUpUserWithEmailCommand)

    @DistributedLock(key = "#command.email", leaseTime = 5L, waitTime = 5L)
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
