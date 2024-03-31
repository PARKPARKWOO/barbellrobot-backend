package com.example.core.user.member.adapter.out

import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.sign.application.port.`in`.command.SignInWithEmailCommand
import com.example.core.sign.application.port.`in`.command.SignUpMemberWithEmailCommand
import com.example.core.user.application.port.command.UpdateProfileCommand
import com.example.core.user.member.adapter.out.persistence.entity.MemberEntity
import com.example.core.user.member.adapter.out.persistence.entity.MemberInfo
import com.example.core.user.member.adapter.out.persistence.repository.MemberRepository
import com.example.core.user.member.application.out.MemberJpaPort
import com.example.domain.user.Member
import com.example.domain.user.Role
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class MemberJpaAdapter(
    private val memberRepository: MemberRepository,
) : MemberJpaPort {
    override fun signUpMember(command: SignUpMemberWithEmailCommand) {
        val memberInfo = MemberInfo(
            tall = command.tall,
            weight = command.weight,
            age = command.age,
            skeletalMuscleMass = command.skeletalMuscleMass,
            exerciseMonths = command.exerciseMonths,
        )
        val entity = MemberEntity(
            email = command.email,
            nickname = command.nickname,
            password = command.password,
            memberInfo = memberInfo,
            role = Role.ROLE_FREE,
            gender = command.gender,
            provider = null,
            profile = null,
        )
        memberRepository.save(entity)
    }

    override fun getMember(id: UUID): Member {
        return getEntity(id).toDomain()
    }

    override fun signInWithEmail(command: SignInWithEmailCommand): Member? {
        return memberRepository.findByEmailAndPassword(
            email = command.email,
            password = command.password,
        )?.toDomain()
    }

    override fun updateProfile(command: UpdateProfileCommand) {
        getEntity(command.userId).uploadProfile(command.uri)
    }

    private fun getEntity(id: UUID): MemberEntity {
        return memberRepository.findById(id).orElseThrow {
            throw ServiceException(ErrorCode.MEMBER_NOT_FOUND)
        }
    }
}
