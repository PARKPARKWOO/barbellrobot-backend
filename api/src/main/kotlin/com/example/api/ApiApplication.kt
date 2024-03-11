package com.example.api

import jakarta.annotation.PostConstruct
import org.joda.time.LocalDate
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import java.util.TimeZone

@SpringBootApplication(
    scanBasePackages = [
        "com.example.core.*",
        "com.example.common.*",
        "com.example.api.*",
    ],
)
@EnableJpaRepositories(
    basePackages = ["com.example.core.*"],
)
@EntityScan(
    basePackages = ["com.example.core.*"],
)
class ApiApplication {
    @PostConstruct
    fun init() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"))
    }
}
@Suppress("SpreadOperator")
fun main(args: Array<String>) {
    runApplication<ApiApplication>(*args)
}
