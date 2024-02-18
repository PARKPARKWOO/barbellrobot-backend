package com.example.core.common.error

data class ServiceException(val errorCode: ErrorCode) : RuntimeException(errorCode.message)
