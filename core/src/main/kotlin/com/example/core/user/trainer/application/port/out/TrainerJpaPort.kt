package com.example.core.user.trainer.application.port.out

import com.example.core.sign.application.port.`in`.command.SignInWithEmailCommand
import com.example.core.user.trainer.application.port.out.command.SignUpTrainerCommand
import com.example.domain.user.Trainer

interface TrainerJpaPort {
    fun signUpTrainer(command: SignUpTrainerCommand): Trainer

    fun signInWithEmail(command: SignInWithEmailCommand): Trainer?
}
