package com.example.api.common.response

data class ApiResponse<T>(
    val success: Boolean = true,
    val data: T?,
)

data class ErrorResponse(
    val success: Boolean = false,
    val code: String,
    val message: String,
)
