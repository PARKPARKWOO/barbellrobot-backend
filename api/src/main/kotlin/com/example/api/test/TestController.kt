package com.example.api.test

import com.example.api.common.annotation.PublicEndPoint
import com.example.common.log.Log
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/test")
class TestController : Log {
    @GetMapping
    @PublicEndPoint
    fun hello(): String {
        log.info("hi")
        return "Hello"
    }
}
