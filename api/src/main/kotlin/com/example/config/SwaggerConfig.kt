package com.example.config

import com.example.config.SwaggerConfig.Companion.AUTHORIZATION_BEARER_SECURITY_SCHEME_NAME
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    @Bean
    fun openAPI(): OpenAPI = OpenAPI()
        .components(
            Components().addSecuritySchemes(
                AUTHORIZATION_BEARER_SECURITY_SCHEME_NAME,
                AuthorizationBearerSecurityScheme,
            ),
        )
        .info(info())

    private fun info() = Info().title("crechat-api")
        .description("crechat API 명세")
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
