package com.example.core.user.member.application.out

import com.example.core.user.application.`in`.command.SignUpMemberFromEmailCommand
import com.example.domain.user.Member
import java.util.UUID

interface MemberPort {
    fun signUpMember(command: SignUpMemberFromEmailCommand)
    fun getMember(id: UUID): Member
}
