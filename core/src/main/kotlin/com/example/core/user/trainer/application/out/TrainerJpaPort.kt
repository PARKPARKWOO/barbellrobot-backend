package com.example.core.user.trainer.application.out

import com.example.core.user.trainer.application.out.command.SignUpTrainerCommand

interface TrainerJpaPort {
    fun signUpTrainer(command: SignUpTrainerCommand)
}
