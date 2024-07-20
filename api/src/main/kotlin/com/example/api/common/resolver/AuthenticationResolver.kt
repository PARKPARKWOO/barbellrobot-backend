package com.example.api.common.resolver

import com.example.api.common.annotation.AuthenticationUser
import com.example.core.common.constants.AuthConstants
import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.user.model.Role
import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import java.util.UUID

@Component
class AuthenticationResolver : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(AuthenticationUser::class.java)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?,
    ): Any? {
        webRequest.getNativeRequest(HttpServletRequest::class.java)?.let {
            val userId = UUID.fromString(it.getAttribute(AuthConstants.USER_ID).toString())
                ?: throw ServiceException(ErrorCode.AUTHENTICATION_RESOLVER_ERROR)
            val role: Role = Role.valueOf(it.getAttribute(AuthConstants.USER_ROLE).toString()) as? Role
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
