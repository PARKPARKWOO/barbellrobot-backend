package com.example.application.sign

import com.example.application.common.constants.Constants
import com.example.core.sign.dto.JwtResponseDto
import com.example.core.common.constants.AuthConstants
import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.sign.port.`in`.JwtReissueUseCase
import com.example.core.nosql.KeyValueStore
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class JwtReissueService(
    private val jwtTokenService: JwtTokenService,
    private val keyValueStore: KeyValueStore,
    @Value("\${jwt.refresh-token.expire-millis}")
    private val refreshTokenExpireTime: Long,
) : JwtReissueUseCase {
    override fun reissueToken(refreshToken: String): JwtResponseDto {
        val claim = jwtTokenService.parseRefreshToken(refreshToken)
        val id = claim[AuthConstants.USER_ID].toString()
        val value = keyValueStore.getValue(Constants.REFRESH_TOKEN_PREFIX + id, String::class.java)
        if (refreshToken == value) {
            val newToken = jwtTokenService.build(claim)
            saveRefreshToken(newToken.refreshToken, id)
            return newToken
        }
        throw ServiceException(ErrorCode.REISSUE_JWT_TOKEN_FAILURE)
    }

    private fun saveRefreshToken(refreshToken: String, id: String) {
        keyValueStore.setValue(
            key = Constants.REFRESH_TOKEN_PREFIX + id,
            value = refreshToken,
            ttl = refreshTokenExpireTime,
        )
    }
}
