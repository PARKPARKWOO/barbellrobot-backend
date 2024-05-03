package com.example.api.common.interceptor

import com.example.common.jwt.JwtTokenService
import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.domain.constants.DomainConstants
import com.example.domain.user.Role
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class TrainerInterceptor(
    private val jwtTokenService: JwtTokenService,
) : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (request.method.equals(HttpMethod.OPTIONS.name())) {
            return true
        }
        val token = request.getBearerTokenFromHeader()
        val claim = jwtTokenService.parseAccessToken(token)
        if (claim[DomainConstants.USER_ROLE] != Role.ROLE_TRAINER) {
            throw ServiceException(ErrorCode.NOT_GRANT_TRAINER_ROLE)
        }
        request.setAttribute(DomainConstants.USER_ID, claim[DomainConstants.USER_ID])
        request.setAttribute(DomainConstants.USER_ROLE, claim[DomainConstants.USER_ROLE])
        return true
    }
}
