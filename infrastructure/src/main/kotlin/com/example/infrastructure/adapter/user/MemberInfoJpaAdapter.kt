package com.example.infrastructure.adapter.user

import com.example.infrastructure.persistence.repository.member.MemberInfoRepository
import com.example.core.user.member.application.command.SaveMemberInfoCommand
import com.example.core.user.member.application.command.UpdateNicknameCommand
import com.example.core.user.member.application.out.MemberInfoJpaPort
import com.example.core.user.member.dto.MemberDetailQueryDto
import com.example.core.user.model.MemberInfo
import org.springframework.stereotype.Component
import java.util.UUID
import kotlin.jvm.optionals.getOrNull
import com.example.core.user.member.adapter.out.persistence.entity.MemberInfo as MemberInfoEntity

@Component
class MemberInfoJpaAdapter(
    private val memberInfoRepository: MemberInfoRepository,
) : MemberInfoJpaPort {
    override fun update(memberInfo: MemberInfo) {
        getEntity(memberInfo.memberId)?.update(memberInfo)
    }

    override fun getInfo(memberId: UUID): MemberInfoEntity? = getEntity(memberId)

    override fun updateNickname(command: UpdateNicknameCommand) {
        getEntity(command.id)?.updateNickname(command.nickname)
    }

    override fun save(command: SaveMemberInfoCommand) {
        val memberInfo = MemberInfoEntity(
            userId = command.memberId,
            nickname = command.nickname,
            tall = command.tall,
            weight = command.weight,
            skeletalMuscleMass = command.skeletalMuscleMass,
            gender = command.gender,
            age = command.age,
            exerciseMonths = command.exerciseMonths,
        )
        memberInfoRepository.save(memberInfo)
    }

    override fun getDetail(memberId: UUID): MemberDetailQueryDto? = memberInfoRepository.findMemberDetailQuery(memberId)

    private fun getEntity(memberId: UUID): MemberInfoEntity? =
        memberInfoRepository
            .findById(memberId)
            .getOrNull()
}
