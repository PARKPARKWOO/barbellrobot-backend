package com.example.core.user.trainer.application.out.command

import com.example.domain.user.Gender

data class SignUpTrainerCommand(
    val email: String,
    val nickname: String,
    val password: String,
    val gender: Gender,
    val exerciseYears: Int,
    val gymName: String,
    val street: String,
    val city: String,
    val country: String,
    val introduce: String,
)
