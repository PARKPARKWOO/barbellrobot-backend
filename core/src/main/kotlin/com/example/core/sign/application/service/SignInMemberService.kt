package com.example.core.sign.application.service

import com.example.common.jwt.JwtTokenService
import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.common.redis.RedisDriver
import com.example.core.sign.application.port.`in`.SignInMemberUseCase
import com.example.core.sign.application.port.`in`.command.SignInWithEmailCommand
import com.example.core.user.member.application.out.MemberJpaPort
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SignInMemberService(
    private val memberJpaPort: MemberJpaPort,
    private val jwtTokenService: JwtTokenService,
    private val redisDriver: RedisDriver,
    @Value("\${jwt.refresh-token.expire-millis}")
    private val refreshTokenExpireTime: Long,
) : SignInMemberUseCase, AbstractSignInService(redisDriver, refreshTokenExpireTime, jwtTokenService) {
    @Transactional(readOnly = true)
    override fun findUser(command: SignInWithEmailCommand): Map<String, Any> {
        return memberJpaPort.signInWithEmail(command)?.getClaims()
            ?: throw ServiceException(ErrorCode.MEMBER_NOT_FOUND)
    }
}
