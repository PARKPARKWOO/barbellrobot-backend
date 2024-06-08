package com.example.core.jwt.application.service

import com.example.common.constants.Constants
import com.example.common.jwt.JwtResponseDto
import com.example.common.jwt.JwtTokenService
import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.common.redis.RedisDriver
import com.example.core.jwt.application.port.`in`.JwtReissueUseCase
import com.example.domain.constants.DomainConstants
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class JwtReissueService(
    private val jwtTokenService: JwtTokenService,
    private val redisDriver: RedisDriver,
    @Value("\${jwt.refresh-token.expire-millis}")
    private val refreshTokenExpireTime: Long,
) : JwtReissueUseCase {
    override fun reissueToken(refreshToken: String): JwtResponseDto {
        val claim = jwtTokenService.parseRefreshToken(refreshToken)
        val id = claim[DomainConstants.USER_ID].toString()
        val value = redisDriver.getValue(Constants.REFRESH_TOKEN_PREFIX + id, String::class.java)
        if (refreshToken == value) {
            val newToken = jwtTokenService.build(claim)
            saveRefreshToken(newToken.refreshToken, id)
            return newToken
        }
        throw ServiceException(ErrorCode.REISSUE_JWT_TOKEN_FAILURE)
    }

    private fun saveRefreshToken(refreshToken: String, id: String) {
        redisDriver.setValue(
            key = Constants.REFRESH_TOKEN_PREFIX + id,
            value = refreshToken,
            ttl = refreshTokenExpireTime,
        )
    }
}
