package com.example.api.common.resolver

import com.example.api.common.annotation.AuthenticationUser
import com.example.common.constants.Constants
import com.example.common.jwt.JwtTokenService
import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.domain.constants.DomainConstants
import com.example.domain.user.Role
import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import java.util.UUID

@Component
class AuthenticationResolver(
    private val jwtTokenService: JwtTokenService,
) : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(AuthenticationUser::class.java)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?,
    ): Any? {
        val request = webRequest.getNativeRequest(HttpServletRequest::class.java)
        request?.let {
            val token = it.getHeader(Constants.AUTHORIZATION_HEADER)
            val claims = jwtTokenService.parse(token)
            val userId = claims[DomainConstants.USER_ID] as? UUID
                ?: throw ServiceException(ErrorCode.AUTHENTICATION_RESOLVER_ERROR)
            val role = claims[DomainConstants.USER_ROLE] as? Role
                ?: throw ServiceException(ErrorCode.AUTHENTICATION_RESOLVER_ERROR)
            return UserInfo(
                userId = userId,
                role = role,
            )
        }
        throw ServiceException(ErrorCode.NOT_FOUND_REQUEST)
    }
}

data class UserInfo(
    val userId: UUID,
    val role: Role,
)
