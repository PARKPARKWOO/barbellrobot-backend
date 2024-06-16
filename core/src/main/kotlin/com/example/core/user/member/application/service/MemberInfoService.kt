package com.example.core.user.member.application.service

import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.common.util.Tx
import com.example.core.user.application.port.out.UserQueryPort
import com.example.core.user.member.application.command.SaveMemberInfoCommand
import com.example.core.user.member.application.command.UpdateMemberInfoCommand
import com.example.core.user.member.application.command.UpdateNicknameCommand
import com.example.core.user.member.application.`in`.MemberInfoUseCase
import com.example.core.user.member.application.out.MemberInfoJpaPort
import com.example.core.user.member.dto.MemberDetailQueryDto
import com.example.domain.user.MemberInfo
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class MemberInfoService(
    private val memberInfoJpaPort: MemberInfoJpaPort,
    private val userQueryPort: UserQueryPort,
) : MemberInfoUseCase {
    override fun update(command: UpdateMemberInfoCommand) {
        getInfo(command.id)?.let {
            val domain = it.toDomain()
            domain.updateInfo(
                exerciseMonths = command.exerciseMonths,
                tall = command.tall,
                weight = command.weight,
                skeletalMuscleMass = command.skeletalMuscleMass,
                age = command.age,
                gender = command.gender,
            )
            memberInfoJpaPort.update(domain)
        }
    }

    override fun getInfo(memberId: UUID): com.example.core.user.member.adapter.out.persistence.entity.MemberInfo? {
        return memberInfoJpaPort.getInfo(memberId)
    }

    override fun updateNickname(command: UpdateNicknameCommand) {
        val memberDetail = memberInfoJpaPort.getInfo(command.id)
        if (memberDetail?.nickname != command.nickname) {
            // nickname 중복시
            userQueryPort.findByNickname(command.nickname)?.let {
                throw ServiceException(ErrorCode.DUPLICATE_NICKNAME)
            } ?: run {
                memberInfoJpaPort.updateNickname(command)
            }
        }
    }

    override fun save(command: SaveMemberInfoCommand) {
        memberInfoJpaPort.save(command)
    }

    override fun getDetail(memberId: UUID): MemberDetailQueryDto {
        return Tx.readTx { memberInfoJpaPort.getDetail(memberId) }
            ?: throw ServiceException(ErrorCode.MEMBER_NOT_FOUND)
    }
}
