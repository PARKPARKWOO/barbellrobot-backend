package com.example.core.nosql

interface KeyValueStore {
    fun <T> setValue(key: String, value: T, ttl: Long)

    fun <T> getValue(key: String, clazz: Class<T>): T?
}
