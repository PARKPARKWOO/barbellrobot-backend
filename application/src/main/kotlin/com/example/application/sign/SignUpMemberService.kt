package com.example.application.sign

import com.example.application.common.transaction.Tx
import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.mail.application.port.out.EmailVerifyPort
import com.example.core.rival.port.`in`.RivalUseCase
import com.example.core.sign.port.`in`.SignUpMemberUseCase
import com.example.core.sign.port.`in`.command.SignUpMemberWithEmailCommand
import com.example.core.sign.port.`in`.command.SignUpUserWithEmailCommand
import com.example.core.user.port.command.SaveMemberInfoCommand
import com.example.core.user.port.`in`.MemberInfoUseCase
import com.example.core.user.port.out.MemberJpaPort
import com.example.core.user.port.out.UserQueryPort
import org.springframework.stereotype.Service

@Service
class SignUpMemberService(
    private val userQueryPort: UserQueryPort,
    private val emailVerifyPort: EmailVerifyPort,
    private val memberJpaPort: MemberJpaPort,
    private val memberInfoUseCase: MemberInfoUseCase,
    private val rivalUseCase: RivalUseCase,
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
            rivalUseCase.createRival(memberId)
            memberInfoUseCase.save(saveMemberInfoCommand)
        }
    }
}
