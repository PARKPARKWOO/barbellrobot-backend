package com.example.`in`.common.interceptor

import com.example.`in`.common.annotation.PublicEndPoint
import com.example.application.common.constants.Constants.AUTHORIZATION_HEADER
import com.example.application.common.constants.Constants.BEARER_PREFIX
import com.example.application.sign.JwtTokenService
import com.example.core.common.constants.AuthConstants
import com.example.core.sign.exception.NoBearerTokenException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor

@Component
class JwtTokenInterceptor(
    private val jwtTokenService: JwtTokenService,
) : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (request.method.equals(HttpMethod.OPTIONS.name())) {
            return true
        }
        val handlerMethod = handler as? HandlerMethod
        val publicAnnotation = handlerMethod?.getMethodAnnotation(PublicEndPoint::class.java)
        if (publicAnnotation != null) {
            return true
        }
        val token = request.getBearerTokenFromHeader()
        val claim = jwtTokenService.parseAccessToken(token)
        request.setAttribute(AuthConstants.USER_ID, claim[AuthConstants.USER_ID])
        request.setAttribute(AuthConstants.USER_ROLE, claim[AuthConstants.USER_ROLE])
        return true
    }
}

fun HttpServletRequest.getBearerTokenFromHeader(): String {
    this.getHeader(AUTHORIZATION_HEADER)?.let { token ->
        when {
            token.startsWith(BEARER_PREFIX) -> return token.substring(
                BEARER_PREFIX.length,
            )

            else -> throw NoBearerTokenException("authorization header contains $token")
        }
    } ?: throw NoBearerTokenException("authorization header does not exists")
}
