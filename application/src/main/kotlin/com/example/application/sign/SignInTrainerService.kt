package com.example.application.sign

import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.nosql.KeyValueStore
import com.example.core.sign.port.`in`.SignInTrainerUseCase
import com.example.core.sign.port.`in`.command.SignInWithEmailCommand
import com.example.core.sign.port.`in`.query.FindUserWithSocialQuery
import com.example.core.sign.port.out.KaKaoSignInPort
import com.example.core.user.port.out.TrainerJpaPort
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SignInTrainerService(
    private val trainerJpaPort: TrainerJpaPort,
    private val jwtTokenService: JwtTokenService,
    private val keyValueStore: KeyValueStore,
    @Value("\${jwt.refresh-token.expire-millis}")
    private val refreshTokenExpireTime: Long,
    private val kaKaoSignInPort: KaKaoSignInPort,
) : AbstractSignInService(keyValueStore, refreshTokenExpireTime, jwtTokenService, kaKaoSignInPort),
    SignInTrainerUseCase {
    @Transactional(readOnly = true)
    override fun findUserWithEmail(command: SignInWithEmailCommand): Map<String, Any> =
        trainerJpaPort.signInWithEmail(command)?.getClaims()
            ?: throw ServiceException(ErrorCode.MEMBER_NOT_FOUND)

    override fun findUserWithSocial(query: FindUserWithSocialQuery): Map<String, Any> {
        TODO()
    }
}
