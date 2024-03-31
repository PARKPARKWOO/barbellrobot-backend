package com.example.core.user.member.application.service

import com.example.core.multimedia.application.port.`in`.MultimediaUploadUseCase
import com.example.core.user.application.port.command.UpdateProfileCommand
import com.example.core.user.application.service.AbstractUserService
import com.example.core.user.member.application.`in`.MemberUseCase
import com.example.core.user.member.application.out.MemberJpaPort
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val multimediaUploadUseCase: MultimediaUploadUseCase,
    private val memberJpaPort: MemberJpaPort,
) : MemberUseCase, AbstractUserService(multimediaUploadUseCase) {
    override fun updateProfile(command: UpdateProfileCommand) {
        memberJpaPort.updateProfile(command)
    }
}
