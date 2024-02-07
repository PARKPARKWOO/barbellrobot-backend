package com.example.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication(
    scanBasePackages = [
        "com.example.core.*",
        "com.example.common.*",
    ],
)
@EnableJpaRepositories(
    basePackages = ["com.example.core.*"],
)
@EntityScan(
    basePackages = ["com.example.core.*"],
)
class ApiApplication

@Suppress("SpreadOperator")
fun main(args: Array<String>) {
    runApplication<ApiApplication>(*args)
}
