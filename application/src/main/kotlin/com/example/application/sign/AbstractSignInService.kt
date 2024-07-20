package com.example.application.sign

import com.example.application.common.constants.Constants
import com.example.core.common.constants.AuthConstants
import com.example.core.nosql.KeyValueStore
import com.example.core.sign.dto.JwtResponseDto
import com.example.core.sign.port.`in`.SignInUserUseCase
import com.example.core.sign.port.`in`.command.SignInWithEmailCommand
import com.example.core.sign.port.`in`.command.SignInWithKakaoCommand
import com.example.core.sign.port.`in`.query.FindUserWithSocialQuery
import com.example.core.sign.port.out.KaKaoSignInPort
import com.example.core.user.model.Provider
import org.springframework.beans.factory.annotation.Value

abstract class AbstractSignInService(
    private val keyValueStore: KeyValueStore,
    @Value("\${jwt.refresh-token.expire-millis}")
    private val refreshTokenExpireTime: Long,
    private val jwtTokenService: JwtTokenService,
    private val kaKaoSignInPort: KaKaoSignInPort,
) : SignInUserUseCase {
    abstract override fun findUserWithEmail(command: SignInWithEmailCommand): Map<String, Any>

    abstract override fun findUserWithSocial(query: FindUserWithSocialQuery): Map<String, Any>

    override fun signInWithKakao(command: SignInWithKakaoCommand): JwtResponseDto {
        val kakaoInfo = kaKaoSignInPort.getTokenInfo(accessToken = Constants.BEARER_PREFIX + command.accessToken)
        val findUserWithSocialQuery = FindUserWithSocialQuery(
            id = kakaoInfo.id.toString(),
            provider = Provider.KAKAO,
        )
        val claim = findUserWithSocial(findUserWithSocialQuery)
        val token = jwtTokenService.build(claim)
        saveRefreshToken(token.refreshToken, claim[AuthConstants.USER_ID].toString())
        return token
    }

    override fun signInWithEmail(command: SignInWithEmailCommand): JwtResponseDto {
        val claim = findUserWithEmail(command)
        val token = jwtTokenService.build(claim)
        saveRefreshToken(token.refreshToken, claim[AuthConstants.USER_ID].toString())
        return token
    }

    private fun saveRefreshToken(refreshToken: String, id: String) {
        keyValueStore.setValue(
            key = Constants.REFRESH_TOKEN_PREFIX + id,
            value = refreshToken,
            ttl = refreshTokenExpireTime,
        )
    }
}
