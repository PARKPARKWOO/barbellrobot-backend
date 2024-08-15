package com.example.`in`.sse

import com.example.`in`.common.annotation.AuthenticationUser
import com.example.`in`.common.config.SwaggerConfig
import com.example.`in`.common.resolver.UserInfo
import com.example.infrastructure.adapter.notification.SseConnectionRegistry
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@RestController
@RequestMapping(path = ["/api/v1/sse"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
class SseController {
    companion object {
        // ms 단위 1시간
        private const val SSE_EMITTER_TIMEOUT = 3_600_000L
    }

    @GetMapping("/connect")
    @Operation(
        summary = "sse 연결",
        security = [SecurityRequirement(name = SwaggerConfig.AUTHORIZATION_BEARER_SECURITY_SCHEME_NAME)],
    )
    fun connectedSse(
        @AuthenticationUser
        userInfo: UserInfo,
    ): SseEmitter {
        val response = SseEmitter(SSE_EMITTER_TIMEOUT)
        SseConnectionRegistry.SSE_CONNECTED_USER[userInfo.userId] = response
        return response
    }
}
