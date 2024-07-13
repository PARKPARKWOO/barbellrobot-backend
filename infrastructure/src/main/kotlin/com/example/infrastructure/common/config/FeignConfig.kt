package com.example.infrastructure.common.config

import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration

@Configuration
@EnableFeignClients(basePackages = ["com.example.infrastructure.adapter.oauth"])
class FeignConfig
