package com.example.api.common.exception

import com.example.api.common.response.ErrorResponse
import com.example.core.sign.exception.ExpiredJwtException
import com.example.core.sign.exception.JwtException
import com.example.core.sign.exception.NoBearerTokenException
import com.example.core.sign.exception.ParseJwtFailedException
import com.example.common.log.Log
import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionController : Log {
    @ExceptionHandler(ServiceException::class)
    fun serviceException(
        e: ServiceException,
        request: HttpServletRequest,
    ): ResponseEntity<ErrorResponse> {
        log.error(
            """
            errorCode = ${e.errorCode.name}
            message = ${e.errorCode.message}
            requestPath = ${request.requestURI}
            """.trimIndent(),
        )
        val response = ErrorResponse(
            code = e.errorCode.name,
            message = e.errorCode.message,
        )
        return ResponseEntity.ok(response)
    }

    @ExceptionHandler(JwtException::class)
    fun jwtException(
        e: JwtException,
        request: HttpServletRequest,
    ): ResponseEntity<ErrorResponse> {
        val body: ErrorResponse
        when (e) {
            is ParseJwtFailedException -> {
                body = ErrorResponse(
                    code = ErrorCode.PARSE_JWT_FAILED.name,
                    message = e.message,
                )
            }

            is ExpiredJwtException -> {
                body = ErrorResponse(
                    code = ErrorCode.EXPIRED_JWT.name,
                    message = e.message,
                )
            }

            is NoBearerTokenException -> {
                body = ErrorResponse(
                    code = ErrorCode.NO_BEARER_TOKEN.name,
                    message = e.message,
                )
            }
        }
        log.error(
            """
            errorCode = ${body.code}
            message = ${body.message}
            requestPath = ${request.requestURI}
            """.trimIndent(),
        )
        return ResponseEntity.ok(body)
    }

    @ExceptionHandler(Exception::class)
    fun unKnownException(
        e: Exception,
        request: HttpServletRequest,
    ): ResponseEntity<ErrorResponse> {
        log.error(
            """
            errorCode = ${ErrorCode.UN_KNOWN_EXCEPTION}
            message = ${ErrorCode.UN_KNOWN_EXCEPTION.message}
            requestPath = ${request.requestURI}
            stackTrace = ${e.printStackTrace()}
            """.trimIndent(),
        )
        val response = ErrorResponse(
            code = ErrorCode.UN_KNOWN_EXCEPTION.name,
            message = ErrorCode.UN_KNOWN_EXCEPTION.message,
        )
        return ResponseEntity.ok(response)
    }
}
