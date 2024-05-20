package com.example.core.ai.application.port.`in`

import com.example.core.ai.application.port.command.CallPtCommand

interface GeminiUseCase {
    fun callPt(command: CallPtCommand): String
}
