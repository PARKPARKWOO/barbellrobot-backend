package com.example.api.response

data class ApiResponse<T>(
    val success: Boolean = true,
    val data: T?,
)
