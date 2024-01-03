package com.example.api.response

data class ApiResponse<T>(
    val code: String,
    val data: T,
)
