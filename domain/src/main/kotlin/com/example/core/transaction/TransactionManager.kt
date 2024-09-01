package com.example.core.transaction

interface TransactionManager {
    fun <T> writeTx(function: () -> T): T

    fun <T> readTx(function: () -> T): T

    fun <T> requiresNew(function: () -> T): T
}
