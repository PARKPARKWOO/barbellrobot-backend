package com.example.core.member.application.`in`

import com.example.core.member.application.`in`.command.SignMemberFromEmailCommand
import java.util.UUID

interface SignUpUseCase {
    fun signMemberFromEmail(command: SignMemberFromEmailCommand): UUID
}
