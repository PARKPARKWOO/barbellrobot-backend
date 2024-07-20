package com.example.infrastructure.common.util

import com.example.core.transaction.TransactionManager
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Component
class SpringTransactionManager : TransactionManager {
    @Transactional
    override fun <T> writeTx(function: () -> T): T {
        return function.invoke()
    }

    @Transactional(readOnly = true)
    override fun <T> readTx(function: () -> T): T {
        return function.invoke()
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    override fun <T> requiresNew(function: () -> T): T {
        return function.invoke()
    }
}
