package com.example.`in`.common.config

import com.example.`in`.common.config.SwaggerConfig.Companion.AUTHORIZATION_BEARER_SECURITY_SCHEME_NAME
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class SwaggerConfig {
    @Bean
    open fun openAPI(): OpenAPI = OpenAPI()
        .components(
            Components().addSecuritySchemes(
                AUTHORIZATION_BEARER_SECURITY_SCHEME_NAME,
                AuthorizationBearerSecurityScheme,
            ),
        )
        .info(info())

    private fun info() = Info().title("health-api")
        .description(
            """
            Service(MVP 개발 이후) 명 입력
            을 위한 API 명세서 입니다.
            """.trimIndent(),
        )
        .version("1")

    companion object {
        const val AUTHORIZATION_BEARER_SECURITY_SCHEME_NAME = "Authorization: Bearer ACCESS_TOKEN"
    }
}

val AuthorizationBearerSecurityScheme: SecurityScheme = SecurityScheme()
    .name(AUTHORIZATION_BEARER_SECURITY_SCHEME_NAME)
    .type(SecurityScheme.Type.HTTP)
    .scheme("Bearer")
    .bearerFormat("JWT")
