package com.example.core.sign.application.service

import com.example.common.jwt.JwtTokenService
import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.common.redis.RedisDriver
import com.example.core.sign.application.port.`in`.SignInTrainerUseCase
import com.example.core.sign.application.port.`in`.command.SignInWithEmailCommand
import com.example.core.user.trainer.application.port.out.TrainerJpaPort
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
) : SignInTrainerUseCase, AbstractSignInService(redisDriver, refreshTokenExpireTime, jwtTokenService) {
    @Transactional(readOnly = true)
    override fun findUser(command: SignInWithEmailCommand): Map<String, Any> {
        return trainerJpaPort.signInWithEmail(command)?.getClaims()
            ?: throw ServiceException(ErrorCode.MEMBER_NOT_FOUND)
    }
}
