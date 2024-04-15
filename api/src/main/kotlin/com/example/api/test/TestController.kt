package com.example.api.test

import com.example.api.common.annotation.PublicEndPoint
import com.example.common.log.Log
import com.example.core.sign.application.port.`in`.EmailVerifyUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/test")
class TestController(
    private val emailVerifyUseCase: EmailVerifyUseCase,
) : Log {
    @GetMapping
    @PublicEndPoint
    fun hello(): String {
        emailVerifyUseCase.sendVerifyEmail("wy9295@naver.com")
        return "Hello"
    }
}
