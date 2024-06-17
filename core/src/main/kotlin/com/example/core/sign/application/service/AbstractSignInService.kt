package com.example.core.sign.application.service

import com.example.common.constants.Constants
import com.example.common.jwt.JwtResponseDto
import com.example.common.jwt.JwtTokenService
import com.example.core.common.redis.RedisDriver
import com.example.core.oauth.application.out.KaKaoFeignClient
import com.example.core.sign.application.port.`in`.SignInUserUseCase
import com.example.core.sign.application.port.`in`.command.SignInWithEmailCommand
import com.example.core.sign.application.port.`in`.command.SignInWithKakaoCommand
import com.example.core.sign.application.port.`in`.query.FindUserWithSocialQuery
import com.example.domain.constants.DomainConstants
import com.example.domain.user.Provider
import org.springframework.beans.factory.annotation.Value

abstract class AbstractSignInService(
    private val redisDriver: RedisDriver,
    @Value("\${jwt.refresh-token.expire-millis}")
    private val refreshTokenExpireTime: Long,
    private val jwtTokenService: JwtTokenService,
    private val kaKaoFeignClient: KaKaoFeignClient,
) : SignInUserUseCase {
    abstract override fun findUserWithEmail(command: SignInWithEmailCommand): Map<String, Any>

    abstract override fun findUserWithSocial(query: FindUserWithSocialQuery): Map<String, Any>

    override fun signInWithKakao(command: SignInWithKakaoCommand): JwtResponseDto {
        val kakaoInfo = kaKaoFeignClient.getTokenInfo(accessToken = Constants.BEARER_PREFIX + command.accessToken)
        val findUserWithSocialQuery = FindUserWithSocialQuery(
            id = kakaoInfo.id.toString(),
            provider = Provider.KAKAO,
        )
        val claim = findUserWithSocial(findUserWithSocialQuery)
        val token = jwtTokenService.build(claim)
        saveRefreshToken(token.refreshToken, claim[DomainConstants.USER_ID].toString())
        return token
    }

    override fun signInWithEmail(command: SignInWithEmailCommand): JwtResponseDto {
        val claim = findUserWithEmail(command)
        val token = jwtTokenService.build(claim)
        saveRefreshToken(token.refreshToken, claim[DomainConstants.USER_ID].toString())
        return token
    }

    private fun saveRefreshToken(refreshToken: String, id: String) {
        redisDriver.setValue(
            key = Constants.REFRESH_TOKEN_PREFIX + id,
            value = refreshToken,
            ttl = refreshTokenExpireTime,
        )
    }
}
