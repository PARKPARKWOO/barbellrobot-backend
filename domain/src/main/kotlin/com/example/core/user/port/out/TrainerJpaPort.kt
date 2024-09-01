package com.example.core.user.port.out

import com.example.core.sign.port.`in`.command.SignInWithEmailCommand
import com.example.core.user.port.command.UpdateProfileCommand
import com.example.core.user.port.command.SignUpTrainerCommand
import com.example.core.user.model.Trainer

interface TrainerJpaPort {
    fun signUpTrainer(command: SignUpTrainerCommand): Trainer

    fun signInWithEmail(command: SignInWithEmailCommand): Trainer?

    fun updateProfile(command: UpdateProfileCommand)
}
