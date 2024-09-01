package com.example.`in`.common.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler

@Configuration
open class AsyncSchedulerConfig {
    @Bean
    open fun taskScheduler(): TaskScheduler {
        val taskScheduler = ThreadPoolTaskScheduler()
        taskScheduler.setPoolSize(10)
        taskScheduler.setThreadNamePrefix("scheduled-task-")
        return taskScheduler
    }
}
