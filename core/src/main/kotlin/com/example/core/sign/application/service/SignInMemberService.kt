package com.example.core.sign.application.service

import com.example.common.jwt.JwtTokenService
import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.common.redis.RedisDriver
import com.example.core.oauth.application.out.KaKaoFeignClient
import com.example.core.sign.application.port.`in`.SignInMemberUseCase
import com.example.core.sign.application.port.`in`.command.SignInWithEmailCommand
import com.example.core.sign.application.port.`in`.query.FindUserWithSocialQuery
import com.example.core.user.member.application.command.SaveMemberCommand
import com.example.core.user.member.application.out.MemberJpaPort
import com.example.domain.user.SocialProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class SignInMemberService(
    private val memberJpaPort: MemberJpaPort,
    private val jwtTokenService: JwtTokenService,
    private val redisDriver: RedisDriver,
    @Value("\${jwt.refresh-token.expire-millis}")
    private val refreshTokenExpireTime: Long,
    private val kaKaoFeignClient: KaKaoFeignClient,
) : AbstractSignInService(redisDriver, refreshTokenExpireTime, jwtTokenService, kaKaoFeignClient),
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
            memberJpaPort.save(saveMemberCommand)
        }.getClaims()
}
