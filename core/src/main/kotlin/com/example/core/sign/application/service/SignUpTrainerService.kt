package com.example.core.sign.application.service

import com.example.core.mail.application.port.out.EmailVerifyPort
import com.example.core.sign.application.port.SignUpUserWithEmailCommand
import com.example.core.sign.application.port.`in`.SignUpTrainerUseCase
import com.example.core.sign.application.port.`in`.command.SignUpTrainerWithEmailCommand
import com.example.core.user.application.port.out.UserQueryPort
import com.example.core.user.trainer.application.port.out.TrainerJpaPort
import org.springframework.stereotype.Service

@Service
class SignUpTrainerService(
    private val userQueryPort: UserQueryPort,
    private val emailVerifyPort: EmailVerifyPort,
    private val trainerJpaPort: TrainerJpaPort,
) : SignUpTrainerUseCase, AbstractSignUpService(userQueryPort, emailVerifyPort) {
    override fun saveUser(command: SignUpUserWithEmailCommand) {
        val trainerCommand = command as? SignUpTrainerWithEmailCommand
            ?: TODO("error 정의 필요")
        trainerJpaPort.signUpTrainer(trainerCommand.toSignUpCommand())
    }

    override fun signUpWithKakao() {
        TODO("Not yet implemented")
    }
}
