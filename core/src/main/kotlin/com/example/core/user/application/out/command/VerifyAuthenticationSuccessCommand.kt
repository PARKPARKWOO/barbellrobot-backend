package com.example.core.user.application.out.command

import java.util.UUID

data class VerifyAuthenticationSuccessCommand(
    val email: String,
    val authenticationString: UUID,
)
