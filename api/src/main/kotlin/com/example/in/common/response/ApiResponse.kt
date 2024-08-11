package com.example.`in`.common.response

data class ApiResponse<T>(
    val success: Boolean = true,
    val data: T?,
)

// pagenation 추가

data class ErrorResponse(
    val success: Boolean = false,
    val code: String,
    val message: String,
)
