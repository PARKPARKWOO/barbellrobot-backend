package com.example.api.common.response

data class ApiResponse<T>(
    val success: Boolean = true,
    val data: T?,
)
