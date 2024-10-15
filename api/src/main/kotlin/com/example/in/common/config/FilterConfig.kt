package com.example.`in`.common.config

import com.example.`in`.common.filter.MDCInitializationFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class FilterConfig {
    @Bean
    open fun requestResponseFilter(): FilterRegistrationBean<MDCInitializationFilter> = FilterRegistrationBean<MDCInitializationFilter>()
        .apply {
            this.filter = MDCInitializationFilter()
            this.order = Int.MIN_VALUE
        }
}
