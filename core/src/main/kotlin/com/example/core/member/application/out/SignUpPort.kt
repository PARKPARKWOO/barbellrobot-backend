package com.example.core.member.application.out

import com.example.core.member.application.`in`.command.SignMemberFromEmailCommand
import java.util.UUID

interface SignUpPort {
    fun signMemberFromEmail(command: SignMemberFromEmailCommand): UUID
}
