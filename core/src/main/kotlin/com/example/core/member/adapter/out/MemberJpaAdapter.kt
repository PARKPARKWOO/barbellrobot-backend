package com.example.core.member.adapter.out

import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.member.adapter.out.persistence.entity.MemberEntity
import com.example.core.member.adapter.out.persistence.entity.MemberInfo
import com.example.core.member.adapter.out.persistence.repository.MemberRepository
import com.example.core.member.application.`in`.command.SignUpMemberFromEmailCommand
import com.example.core.member.application.out.MemberPort
import com.example.core.member.application.out.SignUpPort
import com.example.domain.member.Member
import com.example.domain.member.Role
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class MemberJpaAdapter(
    private val memberRepository: MemberRepository,
) : SignUpPort, MemberPort {
    override fun signUpMemberFromEmail(command: SignUpMemberFromEmailCommand) {
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
        )
        memberRepository.save(entity)
    }

    override fun getMember(id: UUID): Member {
        return getEntity(id).toDomain()
    }

    override fun getMember(nickname: String): Member? {
        return getEntity(nickname)?.toDomain()
    }

    private fun getEntity(id: UUID): MemberEntity {
        return memberRepository.findById(id).orElseThrow {
            throw ServiceException(ErrorCode.MEMBER_NOT_FOUND)
        }
    }

    private fun getEntity(nickname: String): MemberEntity? {
        return memberRepository.findByNickname(nickname)
    }
}
