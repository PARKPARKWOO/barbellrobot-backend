package com.example.core.user.member.application.out

import com.example.core.user.application.`in`.command.SignInWithEmailCommand
import com.example.core.user.member.application.`in`.command.SignUpMemberFromEmailCommand
import com.example.domain.user.Member
import java.util.UUID

interface MemberJpaPort {
    fun signUpMember(command: SignUpMemberFromEmailCommand)

    fun getMember(id: UUID): Member

    fun signInWithEmail(command: SignInWithEmailCommand): Member?
}
