package com.example.core.user.trainer.application.service

import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.user.application.`in`.command.SignUpTrainerFromEmailCommand
import com.example.core.user.application.out.EmailVerifyPort
import com.example.core.user.application.out.SignUpPort
import com.example.core.user.trainer.application.`in`.SignUpTrainerUseCase
import com.example.core.user.trainer.application.out.TrainerJpaPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TrainerService(
    private val signUpPort: SignUpPort,
    private val trainerJpaPort: TrainerJpaPort,
    private val emailVerifyPort: EmailVerifyPort,
) : SignUpTrainerUseCase {
    @Transactional
    override fun signUpTrainerFromEmail(command: SignUpTrainerFromEmailCommand) {
        createVerify(command)
        emailVerifyPort.verifyAuthenticationSuccess(command.toAuthenticationCommand())
        trainerJpaPort.signUpTrainer(command.toSignUpCommand())
    }

    private fun createVerify(command: SignUpTrainerFromEmailCommand) {
        verifyNickname(command.nickname)
    }

    private fun verifyNickname(nickname: String) {
        if (signUpPort.findNicknameUser(nickname)) {
            throw ServiceException(ErrorCode.DUPLICATE_NICKNAME)
        }
    }
}
