package com.example.application.sign

import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.nosql.KeyValueStore
import com.example.core.rival.port.`in`.RivalUseCase
import com.example.core.sign.port.`in`.SignInMemberUseCase
import com.example.core.sign.port.`in`.command.SignInWithEmailCommand
import com.example.core.sign.port.`in`.query.FindUserWithSocialQuery
import com.example.core.sign.port.out.KaKaoSignInPort
import com.example.core.user.model.SocialProvider
import com.example.core.user.port.command.SaveMemberCommand
import com.example.core.user.port.out.MemberJpaPort
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class SignInMemberService(
    private val memberJpaPort: MemberJpaPort,
    private val jwtTokenService: JwtTokenService,
    private val keyValueStore: KeyValueStore,
    @Value("\${jwt.refresh-token.expire-millis}")
    private val refreshTokenExpireTime: Long,
    private val kaKaoSignInPort: KaKaoSignInPort,
    private val rivalUseCase: RivalUseCase,
) : AbstractSignInService(keyValueStore, refreshTokenExpireTime, jwtTokenService, kaKaoSignInPort),
    SignInMemberUseCase {
    @Transactional(readOnly = true)
    override fun findUserWithEmail(command: SignInWithEmailCommand): Map<String, Any> =
        memberJpaPort.signInWithEmail(command)?.getClaims()
            ?: throw ServiceException(ErrorCode.MEMBER_NOT_FOUND)

    override fun findUserWithSocial(query: FindUserWithSocialQuery): Map<String, Any> =
        memberJpaPort.getMember(query)?.getClaims() ?: run {
            val saveMemberCommand = SaveMemberCommand(
                email = "",
                password = "",
                socialProvider = SocialProvider(
                    socialId = query.id,
                    provider = query.provider,
                ),
            )
            val member = memberJpaPort.save(saveMemberCommand)
            rivalUseCase.createRival(member.id)
            member
        }.getClaims()
}
