package com.example.core.mail.application.port.command

import java.util.UUID

data class VerifyAuthenticationSuccessCommand(
    val email: String,
    val authenticationString: UUID,
)
