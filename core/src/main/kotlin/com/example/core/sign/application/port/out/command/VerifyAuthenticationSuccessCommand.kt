package com.example.core.sign.application.port.out.command

import java.util.UUID

data class VerifyAuthenticationSuccessCommand(
    val email: String,
    val authenticationString: UUID,
)
