package com.example.application.sign

import com.example.common.jwt.JwtTokenService
import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.common.redis.RedisDriver
import com.example.core.oauth.application.out.KaKaoFeignClient
import com.example.core.sign.port.`in`.SignInTrainerUseCase
import com.example.core.sign.port.`in`.command.SignInWithEmailCommand
import com.example.core.sign.port.`in`.query.FindUserWithSocialQuery
import com.example.core.user.port.out.TrainerJpaPort
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SignInTrainerService(
    private val trainerJpaPort: TrainerJpaPort,
    private val jwtTokenService: JwtTokenService,
    private val redisDriver: RedisDriver,
    @Value("\${jwt.refresh-token.expire-millis}")
    private val refreshTokenExpireTime: Long,
    private val kaKaoFeignClient: KaKaoFeignClient,
) : AbstractSignInService(redisDriver, refreshTokenExpireTime, jwtTokenService, kaKaoFeignClient),
    SignInTrainerUseCase {
    @Transactional(readOnly = true)
    override fun findUserWithEmail(command: SignInWithEmailCommand): Map<String, Any> =
        trainerJpaPort.signInWithEmail(command)?.getClaims()
            ?: throw ServiceException(ErrorCode.MEMBER_NOT_FOUND)

    override fun findUserWithSocial(query: FindUserWithSocialQuery): Map<String, Any> {
        TODO()
    }
}
