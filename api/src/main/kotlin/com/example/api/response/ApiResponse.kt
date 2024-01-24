package com.example.api.response

data class ApiResponse<T>(
    val code: Boolean,
    val data: T,
)
