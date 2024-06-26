package com.example.core.user.member.application.service

import com.example.core.common.error.ErrorCode.MEMBER_NOT_FOUND
import com.example.core.common.error.ServiceException
import com.example.core.multimedia.application.port.`in`.MultimediaUploadUseCase
import com.example.core.user.application.port.command.UpdateProfileCommand
import com.example.core.user.application.service.AbstractUserService
import com.example.core.user.member.application.command.UpdateMemberInfoCommand
import com.example.core.user.member.application.`in`.MemberInfoUseCase
import com.example.core.user.member.application.`in`.MemberUseCase
import com.example.core.user.member.application.out.MemberJpaPort
import com.example.core.user.member.dto.MemberAndGoalQueryDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class MemberService(
    private val multimediaUploadUseCase: MultimediaUploadUseCase,
    private val memberJpaPort: MemberJpaPort,
    private val memberInfoUseCase: MemberInfoUseCase,
) : MemberUseCase, AbstractUserService(multimediaUploadUseCase) {
    override fun updateProfile(command: UpdateProfileCommand) {
        memberJpaPort.updateProfile(command)
    }

    @Transactional
    override fun update(command: UpdateMemberInfoCommand) {
//        val memberDetail = memberInfoUseCase.getInfo(command.id)
//            ?: throw ServiceException(ErrorCode.MISSING_DETAIL_INFORMATION)
        memberInfoUseCase.updateNickname(command.toUpdateNickname())
        memberInfoUseCase.update(command)
    }

    @Transactional(readOnly = true)
    override fun getMemberAndGoal(id: UUID): MemberAndGoalQueryDto {
        return memberJpaPort.getMemberAndGoal(id) ?: throw ServiceException(MEMBER_NOT_FOUND)
    }
}
