package com.example.infrastructure.adapter.user

import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.sign.port.`in`.command.SignInWithEmailCommand
import com.example.core.sign.port.`in`.command.SignUpMemberWithEmailCommand
import com.example.core.sign.port.`in`.query.FindUserWithSocialQuery
import com.example.core.user.dto.MemberAndGoalQueryDto
import com.example.core.user.dto.MemberSummaryDto
import com.example.core.user.model.Member
import com.example.core.user.model.Role
import com.example.core.user.port.command.SaveMemberCommand
import com.example.core.user.port.command.UpdateProfileCommand
import com.example.core.user.port.out.MemberJpaPort
import com.example.infrastructure.persistence.entity.member.MemberEntity
import com.example.infrastructure.persistence.entity.member.SocialProvider
import com.example.infrastructure.persistence.repository.member.MemberRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class MemberJpaAdapter(
    private val memberRepository: MemberRepository,
) : MemberJpaPort {
    override fun signUpWithEmailMember(command: SignUpMemberWithEmailCommand): UUID {
        val entity = MemberEntity(
            email = command.email,
            password = command.password,
            role = Role.ROLE_FREE,
            socialProvider = null,
            profile = null,
        )
        return memberRepository.save(entity).id
    }

    override fun save(command: SaveMemberCommand): Member {
        val memberEntity = MemberEntity(
            email = command.email,
            password = command.password,
            socialProvider = command.socialProvider?.let {
                SocialProvider(it.socialId, it.provider)
            },
            profile = null,
            role = Role.ROLE_FREE,
        )
        return memberRepository.save(memberEntity).toDomain()
    }

    override fun getMember(id: UUID): Member = getEntity(id).toDomain()

    override fun getMember(query: FindUserWithSocialQuery): Member? = memberRepository.findWithSocial(query)?.toDomain()

    override fun signInWithEmail(command: SignInWithEmailCommand): Member? {
        val member = memberRepository.findByEmailAndPassword(
            email = command.email,
            password = command.password,
        )
        return member?.toDomain()
    }

    override fun updateProfile(command: UpdateProfileCommand) = getEntity(command.userId).uploadProfile(command.uri)

    override fun getMemberAndGoal(id: UUID): MemberAndGoalQueryDto? {
        return memberRepository.findMemberDetailAndGoal(id)
    }

    override fun getMemberSummary(nickname: String): MemberSummaryDto? {
        return memberRepository.getMemberSummaryDto(nickname)
    }

    private fun getEntity(id: UUID): MemberEntity {
        return memberRepository.findById(id).orElseThrow {
            throw ServiceException(ErrorCode.MEMBER_NOT_FOUND)
        }
    }
}
