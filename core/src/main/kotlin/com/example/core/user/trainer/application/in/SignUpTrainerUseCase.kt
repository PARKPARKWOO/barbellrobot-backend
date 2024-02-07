package com.example.core.user.trainer.application.`in`

import com.example.core.user.trainer.application.`in`.command.SignUpTrainerFromEmailCommand

interface SignUpTrainerUseCase {
    fun signUpTrainerFromEmail(command: SignUpTrainerFromEmailCommand)
}
