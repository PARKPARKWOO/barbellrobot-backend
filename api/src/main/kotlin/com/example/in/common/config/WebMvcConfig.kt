package com.example.`in`.common.config

import com.example.`in`.common.interceptor.JwtTokenInterceptor
import com.example.`in`.common.interceptor.TrainerInterceptor
import com.example.`in`.common.resolver.AuthenticationResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebMvc
open class WebMvcConfig(
    private val jwtTokenInterceptor: JwtTokenInterceptor,
    private val authenticationResolver: AuthenticationResolver,
    private val trainerInterceptor: TrainerInterceptor,
) : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(jwtTokenInterceptor)
            .addPathPatterns("/api/**")
        registry.addInterceptor(trainerInterceptor)
            .addPathPatterns("/api/**")
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOriginPatterns(
                "http://localhost:[*]",
                "https://localhost:[*]",
                "https://wooyoung.duckdns.org",
            )
            .allowedHeaders("*")
            .allowedMethods("*")
    }

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(authenticationResolver)
    }
}
