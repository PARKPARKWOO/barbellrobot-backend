package com.example.core.sign.application.service

import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.common.util.Tx
import com.example.core.mail.application.port.out.EmailVerifyPort
import com.example.core.sign.application.port.SignUpUserWithEmailCommand
import com.example.core.sign.application.port.`in`.SignUpMemberUseCase
import com.example.core.sign.application.port.`in`.command.SignUpMemberWithEmailCommand
import com.example.core.user.application.port.out.UserQueryPort
import com.example.core.user.member.application.command.SaveMemberInfoCommand
import com.example.core.user.member.application.`in`.MemberInfoUseCase
import com.example.core.user.member.application.out.MemberJpaPort
import org.springframework.stereotype.Service

@Service
class SignUpMemberService(
    private val userQueryPort: UserQueryPort,
    private val emailVerifyPort: EmailVerifyPort,
    private val memberJpaPort: MemberJpaPort,
    private val memberInfoUseCase: MemberInfoUseCase,
) : SignUpMemberUseCase, AbstractSignUpService(userQueryPort, emailVerifyPort) {
    override fun saveUser(command: SignUpUserWithEmailCommand) {
        val memberCommand = command as? SignUpMemberWithEmailCommand
            ?: throw ServiceException(ErrorCode.SIGN_UP_COMMAND_TYPE_CASTING_ERROR)
        Tx.writeTx {
            val memberId = memberJpaPort.signUpWithEmailMember(memberCommand)
            val saveMemberInfoCommand = SaveMemberInfoCommand(
                memberId = memberId,
                nickname = command.nickname,
                tall = command.tall,
                weight = command.weight,
                skeletalMuscleMass = command.skeletalMuscleMass,
                gender = command.gender,
                age = command.age,
                exerciseMonths = command.exerciseMonths,
            )
            memberInfoUseCase.save(saveMemberInfoCommand)
        }
    }
}
