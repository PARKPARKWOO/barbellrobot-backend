package com.example.api.common.interceptor

import com.example.api.common.annotation.OnlyTrainer
import com.example.application.sign.JwtTokenService
import com.example.core.common.constants.AuthConstants
import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.user.model.Role
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor

@Component
class TrainerInterceptor(
    private val jwtTokenService: JwtTokenService,
) : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (request.method.equals(HttpMethod.OPTIONS.name())) {
            return true
        }
        val handlerMethod = handler as? HandlerMethod
        handlerMethod?.getMethodAnnotation(OnlyTrainer::class.java) ?: return true
        val token = request.getBearerTokenFromHeader()
        val claim = jwtTokenService.parseAccessToken(token)
        if (claim[AuthConstants.USER_ROLE] != Role.ROLE_TRAINER) {
            throw ServiceException(ErrorCode.NOT_GRANT_TRAINER_ROLE)
        }
        request.setAttribute(AuthConstants.USER_ID, claim[AuthConstants.USER_ID])
        request.setAttribute(AuthConstants.USER_ROLE, claim[AuthConstants.USER_ROLE])
        return true
    }
}
