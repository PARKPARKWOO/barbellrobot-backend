package com.example.core.common.util

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Component
class Tx(
    txAdviceInstance: TxAdvice,
) {
    init {
        txAdvice = txAdviceInstance
    }
    companion object {
        private lateinit var txAdvice: TxAdvice

        fun <T> writeTx(function: () -> T): T {
            return txAdvice.write(function)
        }

        fun <T> readTx(function: () -> T): T {
            return txAdvice.read(function)
        }

        fun <T> requiresNew(function: () -> T): T {
            return txAdvice.requiresNew(function)
        }
    }

    @Component
    class TxAdvice {
        @Transactional
        fun <T> write(function: () -> T): T {
            return function.invoke()
        }

        @Transactional(readOnly = true)
        fun <T> read(function: () -> T): T {
            return function.invoke()
        }

        @Transactional(propagation = Propagation.REQUIRES_NEW)
        fun <T> requiresNew(function: () -> T): T {
            return function.invoke()
        }
    }
}
