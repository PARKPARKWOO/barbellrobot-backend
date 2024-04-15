package com.example.core.mail.application.port.out.command

import java.util.UUID

data class VerifyAuthenticationSuccessCommand(
    val email: String,
    val authenticationString: UUID,
)
