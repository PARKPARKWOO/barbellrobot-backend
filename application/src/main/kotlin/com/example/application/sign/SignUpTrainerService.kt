package com.example.application.sign

import com.example.core.mail.application.port.out.EmailVerifyPort
import com.example.core.sign.port.`in`.command.SignUpUserWithEmailCommand
import com.example.core.sign.port.`in`.SignUpTrainerUseCase
import com.example.core.sign.port.`in`.command.SignUpTrainerWithEmailCommand
import com.example.core.user.port.out.UserQueryPort
import com.example.core.user.port.out.TrainerJpaPort
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
}
