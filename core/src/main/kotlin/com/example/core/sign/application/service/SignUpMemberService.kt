package com.example.core.sign.application.service

import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.sign.application.port.SignUpUserWithEmailCommand
import com.example.core.sign.application.port.`in`.SignUpMemberUseCase
import com.example.core.sign.application.port.`in`.command.SignUpMemberWithEmailCommand
import com.example.core.sign.application.port.out.EmailVerifyPort
import com.example.core.user.application.port.out.UserQueryPort
import com.example.core.user.member.application.out.MemberJpaPort
import org.springframework.stereotype.Service

@Service
class SignUpMemberService(
    private val userQueryPort: UserQueryPort,
    private val emailVerifyPort: EmailVerifyPort,
    private val memberJpaPort: MemberJpaPort,
) : SignUpMemberUseCase, AbstractSignUpService(userQueryPort, emailVerifyPort) {
    override fun saveUser(command: SignUpUserWithEmailCommand) {
        val memberCommand = command as? SignUpMemberWithEmailCommand
            ?: throw ServiceException(ErrorCode.SIGN_UP_COMMAND_TYPE_CASTING_ERROR)
        memberJpaPort.signUpMember(memberCommand)
    }

    override fun signUpWithKakao() {
        TODO()
    }
}
