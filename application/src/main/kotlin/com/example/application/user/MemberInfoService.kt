package com.example.application.user

import com.example.application.common.transaction.Tx
import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.user.port.out.UserQueryPort
import com.example.core.user.port.command.SaveMemberInfoCommand
import com.example.core.user.port.command.UpdateMemberInfoCommand
import com.example.core.user.port.command.UpdateNicknameCommand
import com.example.core.user.port.`in`.MemberInfoUseCase
import com.example.core.user.port.out.MemberInfoJpaPort
import com.example.core.user.dto.MemberDetailQueryDto
import com.example.core.user.model.MemberInfo
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class MemberInfoService(
    private val memberInfoJpaPort: MemberInfoJpaPort,
    private val userQueryPort: UserQueryPort,
) : MemberInfoUseCase {
    override fun update(command: UpdateMemberInfoCommand) {
        getInfo(command.id)?.let {
            it.updateInfo(
                exerciseMonths = command.exerciseMonths,
                tall = command.tall,
                weight = command.weight,
                skeletalMuscleMass = command.skeletalMuscleMass,
                age = command.age,
                gender = command.gender,
            )
            memberInfoJpaPort.update(it)
        }
    }

    override fun getInfo(memberId: UUID): MemberInfo? = memberInfoJpaPort.getInfo(memberId)

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

    override fun save(command: SaveMemberInfoCommand) = memberInfoJpaPort.save(command)

    override fun getDetail(memberId: UUID): MemberDetailQueryDto = Tx.readTx { memberInfoJpaPort.getDetail(memberId) }
        ?: throw ServiceException(ErrorCode.MEMBER_NOT_FOUND)
}
