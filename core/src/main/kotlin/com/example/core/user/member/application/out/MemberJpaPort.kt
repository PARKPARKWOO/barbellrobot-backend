package com.example.core.user.member.application.out

import com.example.core.sign.application.port.`in`.command.SignInWithEmailCommand
import com.example.core.sign.application.port.`in`.command.SignUpMemberWithEmailCommand
import com.example.domain.user.Member
import java.util.UUID

interface MemberJpaPort {
    fun signUpMember(command: SignUpMemberWithEmailCommand)

    fun getMember(id: UUID): Member

    fun signInWithEmail(command: SignInWithEmailCommand): Member?
}
