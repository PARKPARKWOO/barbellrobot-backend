package com.example.core.user.trainer.application.out

import com.example.core.user.application.`in`.command.SignInWithEmailCommand
import com.example.core.user.trainer.application.out.command.SignUpTrainerCommand
import com.example.domain.user.Trainer

interface TrainerJpaPort {
    fun signUpTrainer(command: SignUpTrainerCommand)

    fun signInWithEmail(command: SignInWithEmailCommand): Trainer?
}
