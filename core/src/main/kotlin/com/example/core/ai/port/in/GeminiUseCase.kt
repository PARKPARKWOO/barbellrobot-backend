package com.example.core.ai.port.`in`

import com.example.core.ai.port.command.CallPtCommand

interface GeminiUseCase {
    fun callPt(command: CallPtCommand): String
}
