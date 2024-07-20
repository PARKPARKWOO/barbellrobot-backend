package com.example.core.user.port.out

import com.example.core.sign.port.`in`.command.SignInWithEmailCommand
import com.example.core.sign.port.`in`.command.SignUpMemberWithEmailCommand
import com.example.core.sign.port.`in`.query.FindUserWithSocialQuery
import com.example.core.user.port.command.UpdateProfileCommand
import com.example.core.user.port.command.SaveMemberCommand
import com.example.core.user.dto.MemberAndGoalQueryDto
import com.example.core.user.model.Member
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
