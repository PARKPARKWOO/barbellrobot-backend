package com.example.application.common.transaction

import com.example.core.transaction.TransactionManager
import org.springframework.stereotype.Component

@Component
class Tx(
    private val transactionManager: TransactionManager,
) {
    init {
        txManager = transactionManager
    }

    companion object {
        private lateinit var txManager: TransactionManager

        fun <T> writeTx(function: () -> T): T {
            return txManager.writeTx(function)
        }

        fun <T> readTx(function: () -> T): T {
            return txManager.readTx(function)
        }

        fun <T> requiresNew(function: () -> T): T {
            return txManager.requiresNew(function)
        }
    }
}
