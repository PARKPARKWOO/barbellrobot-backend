package com.example.core.user.member.application.out

import com.example.core.sign.application.port.`in`.command.SignInWithEmailCommand
import com.example.core.sign.application.port.`in`.command.SignUpMemberWithEmailCommand
import com.example.core.sign.application.port.`in`.query.FindUserWithSocialQuery
import com.example.core.user.application.port.command.UpdateProfileCommand
import com.example.core.user.member.application.command.SaveMemberCommand
import com.example.core.user.member.dto.MemberAndGoalQueryDto
import com.example.domain.user.Member
import java.util.UUID

interface MemberJpaPort {
    fun signUpWithEmailMember(command: SignUpMemberWithEmailCommand): UUID

    fun save(command: SaveMemberCommand): Member

    fun getMember(id: UUID): Member

    fun getMember(query: FindUserWithSocialQuery): Member?

    fun signInWithEmail(command: SignInWithEmailCommand): Member?

    fun updateProfile(command: UpdateProfileCommand)

    fun getMemberAndGoal(id: UUID): MemberAndGoalQueryDto?
}
