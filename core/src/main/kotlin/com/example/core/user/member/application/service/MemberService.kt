package com.example.core.user.member.application.service

import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.multimedia.application.port.`in`.MultimediaUploadUseCase
import com.example.core.user.adapter.out.UserQueryAdapter
import com.example.core.user.application.port.command.UpdateProfileCommand
import com.example.core.user.application.service.AbstractUserService
import com.example.core.user.member.application.command.UpdateMemberInfoCommand
import com.example.core.user.member.application.`in`.MemberUseCase
import com.example.core.user.member.application.out.MemberJpaPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val multimediaUploadUseCase: MultimediaUploadUseCase,
    private val memberJpaPort: MemberJpaPort,
    private val userQueryAdapter: UserQueryAdapter,
) : MemberUseCase, AbstractUserService(multimediaUploadUseCase) {
    override fun updateProfile(command: UpdateProfileCommand) {
        memberJpaPort.updateProfile(command)
    }

    @Transactional
    override fun update(command: UpdateMemberInfoCommand) {
        val member = memberJpaPort.getMember(command.id)
        if (member.nickname != command.nickname) {
            // nickname 중복시
            userQueryAdapter.findByNickname(command.nickname)?.let {
                throw ServiceException(ErrorCode.DUPLICATE_NICKNAME)
            } ?: member.updateNickname(command.nickname)
        }
        member.updateInfo(
            exerciseMonths = command.exerciseMonths,
            tall = command.tall,
            weight = command.weight,
            skeletalMuscleMass = command.skeletalMuscleMass,
            gender = command.gender,
        )
        memberJpaPort.update(member)
    }
}
