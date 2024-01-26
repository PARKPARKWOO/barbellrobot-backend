package com.example.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(
    scanBasePackages = [
        "com.example.core.*",
        "com.example.common.*",
    ],
)
class ApiApplication

fun main(args: Array<String>) {
    runApplication<ApiApplication>(*args)
}
